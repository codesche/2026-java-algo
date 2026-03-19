package exercise.hspt.service;

import exercise.hspt.domain.MedicalRecord;
import exercise.hspt.domain.Prescription;
import exercise.hspt.repository.BaseRepository;
import java.util.Optional;

public class MedicalRecordService {

    private final BaseRepository<MedicalRecord> repo;

    public MedicalRecordService(BaseRepository<MedicalRecord> repo) {
        this.repo = repo;
    }

    public void save(MedicalRecord record) {
        repo.save(record);
    }

    public void addPrescription(String recordId, Prescription p) {
        repo.findById(recordId).ifPresent(r -> r.addPrescription(p));
    }

    public Optional<MedicalRecord> find(String id) {
        return repo.findById(id);
    }

    public void delete(String id) {
        repo.delete(id);
    }

}
