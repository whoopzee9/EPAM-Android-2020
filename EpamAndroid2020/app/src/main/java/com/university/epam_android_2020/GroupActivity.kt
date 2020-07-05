package com.university.epam_android_2020

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.university.epam_android_2020.models.MapOfUsers
import com.university.epam_android_2020.models.Place
import kotlinx.android.synthetic.main.activity_group.*

class GroupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        supportActionBar?.title = "Group list"

        val maps = generateSampleData()
        rvMaps.layoutManager = LinearLayoutManager(this)
        rvMaps.adapter = MapsAdapter(this, maps, object: MapsAdapter.OnClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@GroupActivity, MainActivity::class.java)
                intent.putExtra(EXTRA_USER_MAP, maps[position])
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
                startActivity(intent)
            }
        })

        addButton.setOnClickListener {
            showAlertDialog(maps, rvMaps.adapter as MapsAdapter)
        }
    }

    private fun showAlertDialog(mapsOfUsers: MutableList<MapOfUsers>, adapter: MapsAdapter) {
        val placeFormView = LayoutInflater.from(this).inflate(R.layout.dialog_create_place, null)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add new group")
            .setView(placeFormView)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Apply", null)
            .show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            val title = placeFormView.findViewById<EditText>(R.id.etTitle).text.toString()

            if (title.trim().isEmpty()) {
                Toast.makeText(this, "Place must have non-empty title", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val newMap = MapOfUsers(title, emptyList<Place>())
            mapsOfUsers.add(newMap)
            adapter.notifyItemInserted(mapsOfUsers.size - 1)
            //need add extra map
            dialog.dismiss()
        }
    }
}

private fun generateSampleData(): MutableList<MapOfUsers> {
    return mutableListOf(
        MapOfUsers(
            "ROOM 1",
            listOf(
                Place("Колян", 37.426, -122.163),
                Place("Димон", 37.430, -122.173),
                Place("Антон", 37.444, -122.170)
            )
        ),
        MapOfUsers(
            "ROOM 2",
            mutableListOf(
                Place("Мучича", 35.67, 139.65),
                Place("Ахмуд", 23.34, 85.31),
                Place("Тахир", 1.35, 103.82)
            )
        ),
        MapOfUsers(
            "ROOM 3",
            mutableListOf(
                Place("Аня", 1.282, 103.864),
                Place("Саня", 1.319, 103.706),
                Place("Витя", 1.249, 103.830),
                Place("Маша", 1.3138, 103.8159)
            )
        ),
        MapOfUsers(
            "ROOM 4",
            mutableListOf(
                Place(
                    "Михал Палыч Терентьев",
                    41.878,
                    -87.630
                ),
                Place("Аниме228", 42.681, -83.134),
                Place(
                    "Валакас1337",
                    45.777,
                    -84.727
                ),
                Place("Мафиозник", 42.701, -84.482),
                Place(
                    "Зубенко М.П.",
                    42.278,
                    -83.738
                )
            )
        ),
        MapOfUsers(
            "ROOM 5",
            mutableListOf(
                Place("Оффник1", 40.709, -73.941),
                Place("Оффник2", 41.895, -87.625),
                Place("Оффник3", 37.768, -122.422),
                Place("Оффник4", 20.322, 77.739),
                Place("Оффник5", 45.505, -122.635)
            )
        )
    )
}
