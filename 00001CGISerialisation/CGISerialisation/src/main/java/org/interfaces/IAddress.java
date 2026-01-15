package org.interfaces;

import org.databases.entity.Address;

import java.util.List;

public interface IAddress {
    void createAddress(Address address);

    // READ
    Address getAddressById(int addressId);
    List<Address> getAllAddresses();
    List<Address> getAddressesByAccountId(int accountId);

    // UPDATE
    void updateAddress(Address address);

    // DELETE
    void deleteAddress(int addressId);
}
