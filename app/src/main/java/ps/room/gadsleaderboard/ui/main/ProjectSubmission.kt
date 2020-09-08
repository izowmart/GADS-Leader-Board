package ps.room.gadsleaderboard.ui.main

import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_project_submission.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import ps.room.gadsleaderboard.R
import ps.room.gadsleaderboard.api.ApiService
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProjectSubmission : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_submission)
        submit_toolbar.setTitleTextColor(resources.getColor(R.color.colorAccent))

        setSupportActionBar(submit_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        project_submit_btn.setOnClickListener {
            GlobalScope.launch {
                validateFields()
            }

        }

    }

    private suspend fun validateFields() {
        val firstName = fName.text.toString()
        val lastName = lName.text.toString()
        val emailAddress = email.text.toString()
        val gitLink = link.text.toString()

        when {
            TextUtils.isEmpty(firstName) -> {
                fName.error = "Field can't be empty"
            }
            TextUtils.isEmpty(lastName) -> {
                lName.error = "Field can't be empty"
            }
            TextUtils.isEmpty(emailAddress) -> {
                email.error = "Field can't be empty"
            }
            TextUtils.isEmpty(gitLink) -> {
                link.error = "Field can't be empty"
            }
            else -> {
                val retrofit = ServiceBuilder.buildService(ApiService::class.java)
                retrofit.submitProject(
                    emailAddress,
                    firstName,
                    lastName,
                    gitLink
                ).enqueue(object : Callback {
                })
            }
        }
    }
    object ServiceBuilder {
        private val client = OkHttpClient.Builder().build()

        private val retrofit = Retrofit.Builder()
            .baseUrl("http://api.server.com") // change this IP for testing by your actual machine IP
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        fun<T> buildService(service: Class<T>): T{
            return retrofit.create(service)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                this.finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}