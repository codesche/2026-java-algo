package exercise.hspt.service;

import exercise.hspt.domain.Appointment;
import exercise.hspt.repository.BaseRepository;
import java.util.Date;
import java.util.List;

public class AppointmentService {

    private final BaseRepository<Appointment> repo;

    public AppointmentService(BaseRepository<Appointment> repo) {
        this.repo = repo;
    }

    public void reserve(Appointment appt) {
        repo.save(appt);
    }

    public void update(String id, Date newDate) {
        repo.findById(id).ifPresent(a -> a.updateDate(newDate));
    }

    public void cancel(String id) {
        repo.delete(id);
    }

    public List<Appointment> getAll() {
        return repo.findAll();
    }

}
