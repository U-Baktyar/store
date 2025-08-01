package sat.factory.create_user;

import sat.DTO.UserDTO;
import sat.DTO.WorkerDto;
import sat.entity.user.User;
import sat.entity.user.Worker;

import java.time.LocalDate;


public class CreateWorker extends CreatUserFactory<WorkerDto>{


    @Override
    public User createUser(WorkerDto workerDto) {
        Worker worker = new Worker();
        worker.setLogin(workerDto.getLogin());
        worker.setPassword(workerDto.getPassword());
        worker.setEmail(workerDto.getEmail());
        worker.setBirthDate(workerDto.getBirthDate());
        worker.setFirstName(workerDto.getFirstName());
        worker.setLastName(workerDto.getLastName());
        worker.setAddress(workerDto.getAddress());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setUserRole(workerDto.getUserRole());
        return worker;
    }
}

