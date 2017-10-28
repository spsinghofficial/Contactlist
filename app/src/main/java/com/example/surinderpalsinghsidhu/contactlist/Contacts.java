package com.example.surinderpalsinghsidhu.contactlist;

/**
 * Created by surinderpalsinghsidhu on 2017-10-27.
 */
import java.io.Serializable;

public class Contacts implements Serializable{
    private String contact_id=null;
    private String contact_name = null;
    private String contact_phone = null;
    private String contact_email=null;
    private String contact_address=null;
    private byte[] contact_picture = null;

     Contacts () {
        setContactId(null);
        setContactName(null);
         setContactPhone(null);
         setContactEmail(null);
         setContactAddress(null);
        setContactPicture(null);
    }

    Contacts (String contact_id, String contact_name) {
        setContactId(contact_id);
        setContactName(contact_name);
    }

    Contacts (String contact_id, String contact_name,String contact_phone,String contact_email,String contact_address, byte[] contact_picture) {
        setContactId(contact_id);
        setContactName(contact_name);
        setContactPhone(contact_phone);
        setContactEmail(contact_email);
        setContactAddress(contact_address);
        setContactPicture(contact_picture);
    }

    public void setContactId(String contact_id) { this.contact_id = contact_id; }
    public void setContactName(String contact_name) { this.contact_name = contact_name; }
    public void setContactPhone(String contact_phone) { this.contact_phone = contact_phone; }
    public void setContactEmail(String contact_email) { this.contact_email= contact_email; }
    public void setContactAddress(String contact_address) { this.contact_address = contact_address; }
    public void setContactPicture(byte[] contact_picture) { this.contact_picture = contact_picture; }

    public String getContactId() { return this.contact_id ; }
    public String getContactName() { return this.contact_name; }
    public String getContactPhone() { return this.contact_phone; }
    public String getContactEmail() { return this.contact_email; }
    public String getContactAddress() { return this.contact_address; }
    public byte[] getContactPicture() { return this.contact_picture; }

    static final class DbColumns {
        public static String COLUMN_1 = "Id";
        public static String COLUMN_2 = "Name";
        public static String COLUMN_3 = "Phone";
        public static String COLUMN_4 = "Email";
        public static String COLUMN_5 = "Address";
        public static String COLUMN_6 = "Picture";
    }

}