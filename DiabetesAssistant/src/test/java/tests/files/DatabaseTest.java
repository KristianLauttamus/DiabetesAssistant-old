package tests.files;

import com.lauttadev.diabetesassistant.files.Database;
import com.lauttadev.diabetesassistant.models.User;
import java.util.prefs.BackingStoreException;
import static junit.framework.Assert.assertEquals;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DatabaseTest {
    private Database db;
    
    @Before
    public void setUp() {
        this.db = new Database("test");
    }
    
    @After
    public void tearDown() throws BackingStoreException {
        this.db.deleteNode();
    }
    
    @Test
    public void testSavingAUser() throws ParseException {
        db.saveUser(new User("test"));
        
        assertEquals(1, db.getUsers().size());
    }
    
    @Test
    public void testSavingMultipleUsers() throws ParseException {
        db.saveUser(new User("test"));
        db.saveUser(new User("test2"));
        
        assertEquals(2, db.getUsers().size());
    }
}
