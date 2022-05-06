import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.nexam.data.ExamDao
import com.example.nexam.data.ExamRoomDatabase
import com.example.nexam.nEXamViewModel
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class AddExamFragmentTest {

    private lateinit var examDao: ExamDao
    private lateinit var db: ExamRoomDatabase
    private lateinit var nEXamViewModel: nEXamViewModel

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(
            context, ExamRoomDatabase::class.java).build()
        examDao = db.ExamDao()
        nEXamViewModel= nEXamViewModel(examDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun isValid_ShouldReturnTrue() {
        var subject: String= "Test"
        Assert.assertTrue(nEXamViewModel.isEntryValid(subject))
    }

    @Test
    fun isNotValid_ShouldReturnFalse(){
        var subject: String=""
        Assert.assertFalse(nEXamViewModel.isEntryValid(subject))
    }
}