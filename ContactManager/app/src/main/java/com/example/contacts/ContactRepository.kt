package com.example.contacts

import java.time.temporal.TemporalQuery

class ContactRepository(private val contactDao: ContactDao) {

    suspend fun insert(contact: Contact) {
        contactDao.insert(contact)
    }

    suspend fun delete(contact: Contact) {
        contactDao.delete(contact)
    }

    fun getContactsSortedByNameAsc() =
        contactDao.getContactsSortedByNameAsc()

    fun getContactsSortedByNameDesc() =
        contactDao.getContactsSortedByNameDesc()

    fun findContacts(searchQuery: String) =
        contactDao.findContacts(searchQuery)
}