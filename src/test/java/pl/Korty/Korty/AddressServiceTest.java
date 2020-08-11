package pl.Korty.Korty;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.Korty.Korty.model.repositories.AddressRepository;
import pl.Korty.Korty.model.responses.AddressRestModel;
import pl.Korty.Korty.model.services.AddressService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("h2")
public class AddressServiceTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressService addressService;

    @AfterEach
    void tearDown() {
        addressRepository.deleteAll();
    }

    @Test
    void addPureAddress_addNewAddressFromRestModel_AddToDb() {
        final AddressRestModel address = new AddressRestModel("nameStreet",1,2,"nameCity","44-100","nameCountry");

        assertEquals(0, addressRepository.count());
        addressService.add(address);

        assertEquals(1, addressRepository.count());
    }

    @Test
    void updateAddress_updateAddressInDbFromRestModelById_updateAddress() {
        final AddressRestModel address = new AddressRestModel("nameStreet",1,2,"nameCity","44-100","nameCountry");
        Long id = addressService.add(address);
        final AddressRestModel updateAddress = new AddressRestModel("UPDATEnameStreet",10,20,"UPDATEnameCity","0044-10000","UPDATEnameCountry");
        addressService.update(id,updateAddress);
        assertEquals(updateAddress,new AddressRestModel(addressRepository.findById(id).get()) );
    }

    @Test
    void deleteAddress_deleteAddressInDbById_foundAndDeleted() {
        final AddressRestModel address = new AddressRestModel("nameStreet",1,2,"nameCity","44-100","nameCountry");
        Long id = addressService.add(address);
        assertEquals(1,addressRepository.count());
        addressService.deleteByID(id);
        assertEquals(0,addressRepository.count());
    }

    @Test
    void getList_getListOfAllAddresses_ReturnListOf3() {
        final AddressRestModel address1 = new AddressRestModel("nameStreet1",11,21,"nameCity1","44-1001","nameCountry1");
        final AddressRestModel address2 = new AddressRestModel("nameStreet2",12,22,"nameCity2","44-1002","nameCountry2");
        final AddressRestModel address3 = new AddressRestModel("nameStreet3",13,23,"nameCity3","44-1003","nameCountry3");
        addressService.add(address1);
        addressService.add(address2);
        addressService.add(address3);
        assertEquals(3,addressRepository.count());

        List<AddressRestModel> addresses = addressService.getAll();
        assertEquals(addressRepository.findAll().stream().map(AddressRestModel::new).collect(Collectors.toList()), addresses);
    }

    @Test
    @Transactional
    void getOneById_GetOneAddressById_ReturnAddress() {
        final AddressRestModel address = new AddressRestModel("nameStreet",1,2,"nameCity","44-100","nameCountry");
        Long id = addressService.add(address);
        assertEquals(1,addressRepository.count());
        assertEquals(address,addressService.getById(id));
    }

    @Test
    void addNewAddress_addInvalidAddress_RefuseToAdd() {
        final AddressRestModel address = null;
        Long id = addressService.add(address);
        assertEquals(-1,id);
    }

    @Test
    void updateUser_invalidIdAndAddressToUpdate_RefuseToUdpdate() {
        final AddressRestModel address = new AddressRestModel("nameStreet",1,2,"nameCity","44-100","nameCountry");
        Long id = addressService.add(address);
        final AddressRestModel updateAddress = null;
        addressService.update(id+1,updateAddress);

        assertEquals(null,addressService.update(id,updateAddress));
    }
}
