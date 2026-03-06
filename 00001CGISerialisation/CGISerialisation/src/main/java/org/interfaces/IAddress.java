package org.interfaces;

import org.databases.entity.Address;

import java.util.List;

public interface IAddress {
    void createAddress(Address address);
    List<Address> getAllAddresses();

    // UPDATE
    void updateAddress(Address address);

    // DELETE
    void deleteAddress(int addressId);
    List<Address> getAddressesByAccountId(int accountId);


    Address getAddressById(int addressId);
}
