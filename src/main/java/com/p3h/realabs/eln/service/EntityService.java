package com.p3h.realabs.eln.service;

import com.p3h.realabs.eln.model.Experiment;
import com.p3h.realabs.eln.model.Sample;
import com.p3h.realabs.eln.model.Storage;
import com.p3h.realabs.eln.repository.ExperimentRepository;
import com.p3h.realabs.eln.repository.SampleRepository;
import com.p3h.realabs.eln.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityService {
    @Autowired
    private ExperimentRepository expRepo;
    @Autowired
    private SampleRepository sampleRepo;
    @Autowired
    private StorageRepository storageRepo;

    public Experiment saveExperiment(Experiment e) {
        return expRepo.save(e);
    }
    public Sample saveSample(Sample s) { return sampleRepo.save(s); }
    public Storage saveStorage(Storage s) { return storageRepo.save(s); }
}