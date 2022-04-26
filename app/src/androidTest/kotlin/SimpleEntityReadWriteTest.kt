import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nexam.data.Exam
import com.example.nexam.data.ExamDao
import com.example.nexam.data.ExamRoomDatabase
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {
    private lateinit var examDao: ExamDao
    private lateinit var db: ExamRoomDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(
            context, ExamRoomDatabase::class.java).build()
        examDao = db.ExamDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    suspend fun writeUserAndReadInList() {
        /*val exam: Exam = TestUtil.createUser(3).apply {
            setName("george")
        }
        examDao.insert(user)
        val byName = examDao.findUsersByName("george")
        assertThat(byName.get(0), equalTo(user))*/

        val exam: Exam = Exam( 1, "Mathe", "2022-06-14")
        examDao.insert(exam)
        val byId = examDao.getExam(1)
        assertThat(byId.toString(), equalTo(exam.toString()) )

    }
}