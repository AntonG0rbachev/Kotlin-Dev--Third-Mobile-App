package ru.chernov.listapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.chernov.listapp.data.NamesOfFragment
import ru.chernov.listapp.fragments.FacultyFragment
import ru.chernov.listapp.fragments.GroupFragment
import ru.chernov.listapp.repository.AppRepository

class MainActivity : AppCompatActivity() {

    interface Edit {
        fun add()
        fun update()
        fun delete()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcvMain, FacultyFragment.getInstance())
            .addToBackStack(null)
            .commit()
    }

    private var _miAddFac: MenuItem? = null
    private var _miDeleteFac: MenuItem? = null
    private var _miUpdateFac: MenuItem? = null
    private var _miAddGroup: MenuItem? = null
    private var _miDeleteGroup: MenuItem? = null
    private var _miUpdateGroup: MenuItem? = null
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        _miAddFac = menu?.findItem(R.id.miAddFaculty)
        _miDeleteFac = menu?.findItem(R.id.miDeleteFaculty)
        _miUpdateFac = menu?.findItem(R.id.miUpdateFaculty)
        _miAddGroup = menu?.findItem(R.id.miAddGroup)
        _miDeleteGroup = menu?.findItem(R.id.miDeleteGroup)
        _miUpdateGroup = menu?.findItem(R.id.miUpdateGroup)

        updateMenu(activeFragment)

        return true
    }



    private  fun updateMenu(fragmentType: NamesOfFragment){
        _miAddFac?.isVisible = fragmentType == NamesOfFragment.FACULTY
        _miDeleteFac?.isVisible = fragmentType == NamesOfFragment.FACULTY
        _miUpdateFac?.isVisible = fragmentType == NamesOfFragment.FACULTY
        _miAddGroup?.isVisible = fragmentType == NamesOfFragment.GROUP
        _miDeleteGroup?.isVisible = fragmentType == NamesOfFragment.GROUP
        _miUpdateGroup?.isVisible = fragmentType == NamesOfFragment.GROUP
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.miAddFaculty -> {
                FacultyFragment.getInstance().add()
                true
            }
            R.id.miUpdateFaculty -> {
                FacultyFragment.getInstance().update()
                true
            }
            R.id.miDeleteFaculty -> {
                FacultyFragment.getInstance().delete()
                true
            }
            R.id.miAddGroup -> {
                GroupFragment.getInstance().add()
                true
            }
            R.id.miDeleteGroup -> {
                GroupFragment.getInstance().delete()
                true
            }
            R.id.miUpdateGroup -> {
                GroupFragment.getInstance().update()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        AppRepository.getInstance().saveData()
        super.onDestroy()
    }
}