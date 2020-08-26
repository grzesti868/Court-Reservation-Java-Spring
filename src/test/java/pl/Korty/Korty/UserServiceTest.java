package pl.Korty.Korty;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.Korty.Korty.exception.ApiNotFoundException;
import pl.Korty.Korty.exception.ApiRequestException;
import pl.Korty.Korty.model.enums.SexEnum;
import pl.Korty.Korty.model.enums.StatusEnum;
import pl.Korty.Korty.model.repositories.AddressRepository;
import pl.Korty.Korty.model.repositories.UserRepository;
import pl.Korty.Korty.model.responses.AddressRestModel;
import pl.Korty.Korty.model.responses.UserRestModel;
import pl.Korty.Korty.model.services.UserService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("h2")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void addUserWithAddress_addNewUserWithNewAddress_AcceptValidUser() {
        final AddressRestModel address = new AddressRestModel("nameStreet",1,2,"nameCity","44-100","nameCountry");
        final UserRestModel user = new UserRestModel("gregvader","admin123","gregvader@gmail.com","Grzegorz","Stich", SexEnum.Male, StatusEnum.Active, address,null);
        assertEquals(0, userRepository.count());
        userService.add(user);
        assertEquals(1, userRepository.count());
        assertEquals(1, addressRepository.count());

    }

    @Test
    @Transactional
    void deleteUser_deleteUserAndHisAddress_foundAndDeleted() {
        final AddressRestModel address = new AddressRestModel("nameStreet",1,2,"nameCity","44-100","nameCountry");
        final UserRestModel user = new UserRestModel("gregvader","admin123","gregvader@gmail.com","Grzegorz","Stich", SexEnum.Male, StatusEnum.Active, address,null);
        assertEquals(0, userRepository.count());
        Long id = userService.add(user);
        assertEquals(1, userRepository.count());
        assertEquals(1, addressRepository.count());

        userService.deleteByLogin(user.getLogin());
        assertEquals(0, userRepository.count());
        assertEquals(0, addressRepository.count());
    }

    @Test
    @Transactional
    void findUserByLogin_findInDbUserByLogin_returnUser() {
        final AddressRestModel address = new AddressRestModel("nameStreet",1,2,"nameCity","44-100","nameCountry");
        final UserRestModel user = new UserRestModel("gregvader","admin123","gregvader@gmail.com","Grzegorz","Stich", SexEnum.Male, StatusEnum.Active, address,null);
        assertEquals(0, userRepository.count());
        Long id = userService.add(user);
        assertEquals(1, userRepository.count());
        assertEquals(1, addressRepository.count());
        assertEquals(user,userService.getByLogin(user.getLogin()));
    }

    @Test
    @Transactional
    void getListUsers_getAllUsersToList_ReturnList() {

        final UserRestModel user1 = new UserRestModel("gregvader1","admin1231","gregva1233der@gmail.com","Grzegorz","Stich", SexEnum.Male, StatusEnum.Active, new AddressRestModel(),null);
        final UserRestModel user2 = new UserRestModel("gregvader2","admin1232","gregva123der@gmail.com","Grzegorz","Stich", SexEnum.Male, StatusEnum.Active, new AddressRestModel(),null);
        final UserRestModel user3 = new UserRestModel("gregvader3","admin1233","gregvad11er@gmail.com","Grzegorz","Stich", SexEnum.Male, StatusEnum.Active, new AddressRestModel(),null);
        userService.add(user1);
        userService.add(user2);
        userService.add(user3);
        assertEquals(3,addressRepository.count());

        List users = new ArrayList();

        users.add(user1);
        users.add(user2);
        users.add(user3);

        assertEquals(users, userService.getAll());
    }

    @Test
    @Transactional
    void updateUser_updateUserAndHisAddress_successfulUpdate() {
        final AddressRestModel address = new AddressRestModel("nameStreet",1,2,"nameCity","44-100","nameCountry");
        final UserRestModel user = new UserRestModel("gregvader","admin123","gregvader@gmail.com","Grzegorz","Stich", SexEnum.Male, StatusEnum.Active, address,null);
        assertEquals(0, userRepository.count());
        Long id = userService.add(user);
        assertEquals(1, userRepository.count());

        final AddressRestModel updateAddress = new AddressRestModel("UPDATEnameStreet",1,2,"UPDATEnameCity","44-100","nameCountry");
        final UserRestModel updateUser = new UserRestModel("UPDATEgregvader","UPDATEadmin123","gregvader@gmail.com","Grzegorz","Stich", SexEnum.Male, StatusEnum.Active, address,null);

        userService.update(user.getLogin(),updateUser);
        assertEquals(updateUser,userService.getByLogin(updateUser.getLogin()));
    }

    @Test
    void addUser_addEmptyUser_refuseToAdd() {
        assertThrows(ApiRequestException.class,()->userService.add(null));
    }

    @Test
    void addUser_addUSerWithoutAddress_refuseToAddUser() {
        final AddressRestModel address = null;
        final UserRestModel user = new UserRestModel("gregvader","admin123","gregvader@gmail.com","Grzegorz","Stich", SexEnum.Male, StatusEnum.Active, address,null);

        assertThrows(ApiRequestException.class , () -> userService.add(user));
    }

    @Test
    void updateUser_setInvalidLoginAndCorrectUser_throwException() {
        final AddressRestModel address = new AddressRestModel("nameStreet",1,2,"nameCity","44-100","nameCountry");
        final UserRestModel user = new UserRestModel("gregvader","admin123","gregvader@gmail.com","Grzegorz","Stich", SexEnum.Male, StatusEnum.Active, address,null);
        Long id = userService.add(user);
        assertEquals(1, userRepository.count());

        final AddressRestModel updateAddress = new AddressRestModel("UPDATEnameStreet",1,2,"UPDATEnameCity","44-100","nameCountry");
        final UserRestModel updateUser = new UserRestModel("UPDATEgregvader","admin123","UPDATEgregvader@gmail.com","Grzegorz","Stich", SexEnum.Male, StatusEnum.Active, updateAddress,null);

        assertThrows(ApiRequestException.class,()->userService.update("greeegvader",updateUser));
    }

    @Test
    void updateUser_setValidLoginAndIncorrectUser_throwException() {
        final AddressRestModel address = new AddressRestModel("nameStreet",1,2,"nameCity","44-100","nameCountry");
        final UserRestModel user = new UserRestModel("gregvader","admin123","gregvader@gmail.com","Grzegorz","Stich", SexEnum.Male, StatusEnum.Active, address,null);
        Long id = userService.add(user);
        assertEquals(1, userRepository.count());

        final UserRestModel updateUser = null;

        assertThrows(ApiRequestException.class,()->userService.update(user.getLogin(),updateUser));
    }

    @Test
    void deleteUser_cantFindValidUser_throwException() {
        assertThrows(ApiNotFoundException.class, ()->userService.deleteByLogin("ogin"));
    }

    @Test
    void findUserById_cantFindValidUser_throwException() {

        assertThrows(ApiNotFoundException.class,()->userService.getByLogin("someLogin"));

    }

    @Test
    void addUser_addUserWhileLoginExists_refuseToAdd() {
        final AddressRestModel address = new AddressRestModel("nameStreet",1,2,"nameCity","44-100","nameCountry");
        final UserRestModel user = new UserRestModel("gregvader","admin123","gregvader@gmail.com","Grzegorz","Stich", SexEnum.Male, StatusEnum.Active, address,null);
        Long id = userService.add(user);
        assertEquals(1, userRepository.count());
        final AddressRestModel newAddress = new AddressRestModel("asdasdaa",1,2,"nameCity","44-100","nameCountry");
        final UserRestModel newUser = new UserRestModel("gregvader","asdasda","asdasdasd@gmail.com","Grzegorz","Stich", SexEnum.Male, StatusEnum.Active, newAddress,null);


        assertThrows(ApiRequestException.class , () -> userService.add(newUser));
        assertEquals(1, userRepository.count());

    }
}
