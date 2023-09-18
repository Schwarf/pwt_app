package abs.apps.personal_workout_tracker

import abs.apps.personal_workout_tracker.data.database.TrackerDatabase
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val DB_NAME = "test.db"


@RunWith(AndroidJUnit4::class)
class MigrationTest {
    @get:Rule
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        TrackerDatabase::class.java,
        listOf(),
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migration1To2_containsCorrectData() {
        var db = helper.createDatabase(DB_NAME, 1).apply {
            execSQL("INSERT INTO workouts (name, sets, totalRepetitions, maxRepetitionsInSet, performances) VALUES('push-up', 3, 22, 9, 0)")
            close()
        }

        db = helper.runMigrationsAndValidate(DB_NAME, 2, true)

        db.query("SELECT * FROM workouts").apply {
            assertThat(moveToFirst()).isTrue()
            assertThat(getString(getColumnIndex("name"))).isEqualTo("push-up")
            assertThat(getLong(getColumnIndex("sets"))).isEqualTo(3)
            assertThat(getLong(getColumnIndex("totalRepetitions"))).isEqualTo(22)
            assertThat(getLong(getColumnIndex("maxRepetitionsInSet"))).isEqualTo(9)
            assertThat(getLong(getColumnIndex("performances"))).isEqualTo(0)
        }
    }

    @Test
    fun testAllMigrations() {
        helper.createDatabase(DB_NAME, 1).close()
        Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            TrackerDatabase::class.java,
            DB_NAME
        )
            .addMigrations(TrackerDatabase.migration2to3).build()
            .apply { openHelper.writableDatabase.close() }

    }

}