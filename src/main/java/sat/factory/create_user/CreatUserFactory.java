package sat.factory.create_user;


import sat.DTO.UserDTO;
import sat.entity.user.User;

public abstract class CreatUserFactory<T extends UserDTO> {

    public  User getUser(T userDTO){
        return createUser(userDTO);
    }

    abstract User createUser(T userDTO);
}
