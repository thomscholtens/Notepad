package com.example.notepad.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.notepad.R
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*
import java.util.*
import androidx.lifecycle.Observer


class AddActivity : AppCompatActivity() {

    private lateinit var editViewModel: AddActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Edit Notepad"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        fab.setOnClickListener {

            editViewModel.note.value?.apply {
                title = etTitle.text.toString()
                lastUpdated = Date()
                text = etNote.text.toString()
            }

            editViewModel.updateNote()
        }
    }

    private fun initViewModel() {
        editViewModel = ViewModelProviders.of(this).get(AddActivityViewModel::class.java)
        editViewModel.note.value = intent.extras?.getParcelable(EXTRA_NOTE)!!

        editViewModel.note.observe(this, Observer { note ->
            if (note != null) {
                etTitle.setText(note.title)
                etNote.setText(note.text)
            }
        })

        editViewModel.error.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        editViewModel.success.observe(this, Observer { success ->
            if (success) finish()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> { // Used to identify when the user has clicked the back button
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_NOTE = "EXTRA_NOTE"
    }

}

