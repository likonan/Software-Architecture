package 实验3.任务2;

import java.util.List;

public class Server {
    private ContactDao contactDao;

    public Server() {
        contactDao = new ContactDao();
    }

    public List<Contact> getAllContacts() {
        return contactDao.getAllContacts();
    }

    // 其他业务逻辑方法（添加、修改、删除等）省略
    public void addContact(Contact contact) {
        contactDao.addContact(contact);
    }
    public boolean deleteContact(Integer id) {
        return contactDao.deleteContact(id);
    }
    public boolean updateContact(Contact contact) {
        return contactDao.updateContact(contact);
    }
    public boolean insertContact(Contact contact) {
        return contactDao.addContact(contact);
    }
}
