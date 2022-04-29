import android.content.Context
import androidx.lifecycle.asLiveData
import androidx.room.Insert
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nexam.data.Exam
import com.example.nexam.data.ExamDao
import com.example.nexam.data.ExamRoomDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
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
    fun addAndReadExam() = runBlocking{
        val exam: Exam = Exam( 1, "Mathe", "2022-06-14")
        examDao.insert(exam)
        val byId = examDao.getExam(1)
        assertThat(byId.firstOrNull(), equalTo(exam) )
    }
    @Test
    @Throws(Exception::class)
    fun updateExam() = runBlocking {
        val exam: Exam = Exam( 1, "Mathe", "2022-06-14")
        examDao.insert(exam)
        val examUpdated: Exam = Exam(1, "Mathe", "2022-07-01")
        examDao.update(examUpdated)
        val byId = examDao.getExam(1)
        assertThat(byId.firstOrNull(), equalTo(examUpdated) )
    }
    @Test
    @Throws(Exception::class)
    fun deleteExam() = runBlocking{
        val exam: Exam = Exam( 1, "Mathe", "2022-06-14")
        examDao.insert(exam)
        examDao.delete(exam)
        val byId = examDao.getExam(1)
        assertThat(byId.firstOrNull(), equalTo(null))
    }
}