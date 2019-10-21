/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.kit.datamanager.metastore.service.impl;

import edu.kit.datamanager.metastore.entity.ZippedBagit;
import edu.kit.datamanager.metastore.repository.ZippedBagitRepository;
import edu.kit.datamanager.metastore.service.IZippedBagitService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class implementing services for bagit containers.
 */
@Service
public class ZippedBagitService implements IZippedBagitService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ZippedBagitService.class);

    /**
     * Repository persisting bagit containers.
     */
    private ZippedBagitRepository bagitRepository;

    /**
     * Set repository via autowired, to allow initialization.
     *
     * @param bagitRepo
     */
    @Autowired
    public void setBagitRepository(ZippedBagitRepository bagitRepo) {
        this.bagitRepository = bagitRepo;
        long count = bagitRepo.count();
        LOGGER.debug("No of entities in ZippedBagitRepository: {}", count);
    }

    @Override
    public List<ZippedBagit> getAllLatestZippedBagits() {
         List<ZippedBagit> bagitList = new ArrayList<>();
        Iterator<ZippedBagit> iterator = bagitRepository.findByLatestTrueOrderByUploadDateDesc().iterator();
        while (iterator.hasNext()) {
            bagitList.add(iterator.next());
        }
        return bagitList;
    }

    @Override
    public ZippedBagit getMostRecentZippedBagitByOcrdIdentifier(String ocrdIdentifier) {
        ZippedBagit bagit = null;
        Iterator<ZippedBagit> iterator = bagitRepository.findByOcrdIdentifierOrderByVersionDesc(ocrdIdentifier).iterator();
        if (iterator.hasNext()) {
            bagit = iterator.next();
        }
        return bagit;
    }

    @Override
    public List<Integer> getAllVersionsByOcrdIdentifier(String ocrdIdentifier) {
        Iterator<ZippedBagit> findZippedFilesWithOcrdIdentifier = bagitRepository.findByOcrdIdentifierOrderByVersionDesc(ocrdIdentifier).iterator();
        List<Integer> allVersions = new ArrayList<>();
        while (findZippedFilesWithOcrdIdentifier.hasNext()) {
            allVersions.add(findZippedFilesWithOcrdIdentifier.next().getVersion());
        }
        return allVersions;
    }

    @Override
    public ZippedBagit getZippedBagitByOcrdIdentifierAndVersion(String ocrdIdentifier, Integer version) {
        ZippedBagit bagit = null;
        Iterator<ZippedBagit> iterator = bagitRepository.findByOcrdIdentifierAndVersion(ocrdIdentifier, version).iterator();
        if (iterator.hasNext()) {
            bagit = iterator.next();
        }
        return bagit;
    }

    @Override
    public List<ZippedBagit> getZippedBagitsByOcrdIdentifierOrderByVersionDesc(String ocrdIdentifier) {
        List<ZippedBagit> bagitList = new ArrayList<>();
        Iterator<ZippedBagit> iterator = bagitRepository.findByOcrdIdentifierOrderByVersionDesc(ocrdIdentifier).iterator();
        while (iterator.hasNext()) {
            bagitList.add(iterator.next());
        }
        return bagitList;
    }

    @Override
    public ZippedBagit getZippedBagitByResourceId(String resourceIdentifier) {
        ZippedBagit bagit = null;
        Iterator<ZippedBagit> iterator = bagitRepository.findByResourceId(resourceIdentifier).iterator();
        if (iterator.hasNext()) {
            bagit = iterator.next();
        }
        
        return bagit;
    }

    @Override
    public ZippedBagit save(ZippedBagit zippedBagit) {
        return bagitRepository.save(zippedBagit);
    }

}
