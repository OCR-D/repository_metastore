/*
 * Copyright 2018 Karlsruhe Institute of Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.kit.ocrd.workspace;

import edu.kit.datamanager.metastore.exception.BagItException;
import gov.loc.repository.bagit.conformance.BagLinter;
import gov.loc.repository.bagit.creator.BagCreator;
import gov.loc.repository.bagit.domain.Bag;
import gov.loc.repository.bagit.exceptions.CorruptChecksumException;
import gov.loc.repository.bagit.exceptions.FileNotInPayloadDirectoryException;
import gov.loc.repository.bagit.exceptions.InvalidBagitFileFormatException;
import gov.loc.repository.bagit.exceptions.InvalidPayloadOxumException;
import gov.loc.repository.bagit.exceptions.MaliciousPathException;
import gov.loc.repository.bagit.exceptions.MissingBagitFileException;
import gov.loc.repository.bagit.exceptions.MissingPayloadDirectoryException;
import gov.loc.repository.bagit.exceptions.MissingPayloadManifestException;
import gov.loc.repository.bagit.exceptions.UnparsableVersionException;
import gov.loc.repository.bagit.exceptions.UnsupportedAlgorithmException;
import gov.loc.repository.bagit.exceptions.VerificationException;
import gov.loc.repository.bagit.exceptions.conformance.BagitVersionIsNotAcceptableException;
import gov.loc.repository.bagit.exceptions.conformance.FetchFileNotAllowedException;
import gov.loc.repository.bagit.exceptions.conformance.MetatdataValueIsNotAcceptableException;
import gov.loc.repository.bagit.exceptions.conformance.MetatdataValueIsNotRepeatableException;
import gov.loc.repository.bagit.exceptions.conformance.RequiredManifestNotPresentException;
import gov.loc.repository.bagit.exceptions.conformance.RequiredMetadataFieldNotPresentException;
import gov.loc.repository.bagit.exceptions.conformance.RequiredTagFileNotPresentException;
import gov.loc.repository.bagit.hash.StandardSupportedAlgorithms;
import gov.loc.repository.bagit.reader.BagReader;
import gov.loc.repository.bagit.verify.BagVerifier;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility handling BagIt containers.
 */
public class BagItUtil {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BagItUtil.class);
    /**
     * Error message multiple OCR-D identifier
     */
    public static final String MULTIPLE_OCR_D_IDENTIFIER = "Error: Multiple OCRD identifiers defined!";
    /**
     * Error message multiple OCR-D identifier
     */
    public static final String MULTIPLE_METS_LOCATIONS = "Error: Multiple METS locations defined!";
    /**
     * Key inside Bagit container defining OCRD identifier.
     */
    public static final String X_OCRD_IDENTIFIER = "Ocrd-Identifier";
    /**
     * Key inside Bagit container defining location of the METS file.
     */
    public static final String X_OCRD_METS = "Ocrd-Mets";
    /**
     * Default location of the METS file.
     */
    public static final String METS_LOCATION_DEFAULT = "data/mets.xml";

    /**
     * Build BagIt container of a payload directory.
     *
     * @param payLoadPath Path to payload directory.
     *
     * @return Bag of directory.
     * @throws BagItException Error building bag.
     */
    public static Bag buildBag(File payLoadPath) throws BagItException {
        Bag bag = null;
        try {
            Path folder = Paths.get(payLoadPath.getAbsolutePath());
            StandardSupportedAlgorithms algorithm = StandardSupportedAlgorithms.MD5;
            boolean includeHiddenFiles = false;
            bag = BagCreator.bagInPlace(folder, Arrays.asList(algorithm), includeHiddenFiles);

        } catch (NoSuchAlgorithmException | IOException ex) {
            LOGGER.error("Can't create Bag!", ex);
            throw new BagItException(ex.getMessage());
        }
        return bag;
    }

    /**
     * Creating BagIt container of a bagIt directory.
     *
     * @param pathToBag Path to bagIt directory.
     *
     * @return Bag of directory.
     * @throws BagItException Error reading bag.
     */
    public static Bag readBag(Path pathToBag) throws BagItException {
        LOGGER.debug("Read BagIt...");
        Bag bag = null;
        BagReader reader = new BagReader();
        try {
            bag = reader.read(pathToBag);
        } catch (IOException | UnparsableVersionException | MaliciousPathException | UnsupportedAlgorithmException | InvalidBagitFileFormatException ex) {
            LOGGER.error("Can't read Bag!", ex);
            throw new BagItException(ex.getMessage());
        }
        validateBagit(bag);

        return bag;
    }

    /**
     * Validate BagIt container.
     *
     * @param bag Bag to validate.
     *
     * @return true or false
     * @throws BagItException Error validating bag.
     */
    public static boolean validateBagit(Bag bag) throws BagItException {
        boolean valid = true;
        LOGGER.debug("Validate Bag!");
        if (BagVerifier.canQuickVerify(bag)) {
            try {
                BagVerifier.quicklyVerify(bag);
            } catch (IOException | InvalidPayloadOxumException ex) {
                LOGGER.error("PayLoadOxum is invalid: ", ex);
                throw new BagItException(ex.getMessage());
            }
        }
        /////////////////////////////////////////////////////////////////
        // Check for Profile and validate it
        /////////////////////////////////////////////////////////////////
        List<String> url2Profile = bag.getMetadata().get("BagIt-Profile-Identifier");
        Iterator<String> profileIterator = url2Profile.iterator();
        try {
            if (profileIterator.hasNext()) {
                InputStream inputStream4Profile = new URL(profileIterator.next()).openStream();
                BagLinter.checkAgainstProfile(inputStream4Profile, bag);
            }
        } catch (Exception ex) {
            LOGGER.error("Container does not match the defined profile!", ex);
            throw new BagItException(ex.getMessage());
        }
        /////////////////////////////////////////////////////////////////
        // Verify completeness
        /////////////////////////////////////////////////////////////////
        boolean ignoreHiddenFiles = false;
        BagVerifier verifierCompleteness = new BagVerifier();
        try {
            verifierCompleteness.isComplete(bag, ignoreHiddenFiles);
        } catch (IOException | MissingPayloadManifestException | MissingBagitFileException | MissingPayloadDirectoryException | FileNotInPayloadDirectoryException | InterruptedException | MaliciousPathException | UnsupportedAlgorithmException | InvalidBagitFileFormatException ex) {
            LOGGER.error("Bag is not complete!", ex);
            throw new BagItException(ex.getMessage());
        }
        /////////////////////////////////////////////////////////////////
        // Verify validity
        /////////////////////////////////////////////////////////////////
        BagVerifier verifierValidity = new BagVerifier();

        try {
            verifierValidity.isValid(bag, ignoreHiddenFiles);
        } catch (IOException | MissingPayloadManifestException | MissingBagitFileException | MissingPayloadDirectoryException | FileNotInPayloadDirectoryException | InterruptedException | MaliciousPathException | CorruptChecksumException | VerificationException | UnsupportedAlgorithmException | InvalidBagitFileFormatException ex) {
            LOGGER.error("Bag is not valid!", ex);
            throw new BagItException(ex.getMessage());
        }
        printBagItInformation(bag);
        return valid;
    }

    /**
     * Print some information about the BagIt container.
     *
     * @param bag Instance holding BagIt container.
     */
    public static void printBagItInformation(Bag bag) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Version: {}", bag.getVersion());
            bag.getMetadata().getAll().forEach((entry) -> {
                LOGGER.debug("{} : {}", entry.getKey(), entry.getValue());
            });
        }
    }

    /**
     * Determines the path to the METS file.
     *
     * @param bag BagIt container.
     *
     * @return Relative path to METS file.
     */
    public static String getPathToMets(Bag bag) throws BagItException {
        List<String> listOfEntries = bag.getMetadata().get(X_OCRD_METS);
        String pathToMets = METS_LOCATION_DEFAULT;
        if (listOfEntries != null) {
            if (listOfEntries.size() > 1) {
                LOGGER.error("There are multiple location for METS defined!");
                for (String item : listOfEntries) {
                    LOGGER.warn("Found: {}", item);
                }
                throw new BagItException(MULTIPLE_METS_LOCATIONS);
            }
            pathToMets = listOfEntries.get(0);
        }
        LOGGER.trace("Path to METS: {}", pathToMets);
        return pathToMets;
    }

    /**
     * Determines the path to the METS file.
     *
     * @param bag BagIt container.
     *
     * @return Relative path to METS file.
     */
    public static String getOcrdIdentifierOfBag(Bag bag) throws BagItException {
        List<String> listOfEntries = bag.getMetadata().get(X_OCRD_IDENTIFIER);
        String ocrdIdentifier = null;
        if (listOfEntries != null) {
            if (listOfEntries.size() > 1) {
                LOGGER.error("There are multiple OCRD identifiers defined!");
                for (String item : listOfEntries) {
                    LOGGER.warn("Found: {}", item);
                }
                throw new BagItException(MULTIPLE_OCR_D_IDENTIFIER);
            }
            ocrdIdentifier = listOfEntries.get(0);
        }
        LOGGER.trace("OCRD identifier: {}", ocrdIdentifier);
        return ocrdIdentifier;
    }
}
