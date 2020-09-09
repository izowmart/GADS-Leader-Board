package ps.room.gadsleaderboard.ui.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_project_submission.*
import kotlinx.android.synthetic.main.dialog_confirmation.*
import kotlinx.android.synthetic.main.dialog_result_upload.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import ps.room.gadsleaderboard.R
import ps.room.gadsleaderboard.api.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

            val confirmView =
                LayoutInflater.from(applicationContext).inflate(R.layout.dialog_confirmation, null)
            val dialogConfirm = showCustomDialog(confirmView, 200, 100, false)

            btn_confirm.setOnClickListener {
                GlobalScope.launch {
                    validateFields()
                }
                dialogConfirm.dismiss()
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
                val loadingDialog =
                    LayoutInflater.from(applicationContext).inflate(R.layout.loading_dialog, null)
                val loadingView = showCustomDialog(loadingDialog, 200, 100, false)

                val retrofit = ServiceBuilder.buildService(ApiService::class.java)
                retrofit.submitProject(
                    emailAddress,
                    firstName,
                    lastName,
                    gitLink
                ).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            loadingView.dismiss()
                            // Are you sure ->then click of yes btn then submission was successful
                            showCustomDialog(getResultView(true), 350, 200, true)
                        } else {
                            // Submission was unsuccessful
                            loadingView.dismiss()
                            showCustomDialog(getResultView(false), 350, 200, true)
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        // Print out error in a toast displaying the error
                        loadingView.dismiss()
                        showCustomDialog(getResultView(false), 350, 200, true)
                    }

                })
            }
        }
    }

    fun getResultView(successful: Boolean): View {
        return LayoutInflater.from(applicationContext).inflate(R.layout.dialog_result_upload, null)
            .apply {
                if (successful) {
                    // Show the success property
                    ivResultIcon.setImageResource(R.drawable.ic_round_check_circle_24)
                    tvResultText.text = "Submission was successful"
                } else {
                    // Show the error properties
                    ivResultIcon.setImageResource(R.drawable.ic_error)
                    tvResultText.text = "Submission was not successful"
                }
            }
    }

    private fun showCustomDialog(
        view: View,
        requestedWidth: Int,
        requestedHeight: Int,
        cancellable: Boolean
    ): AlertDialog {
        val dialog = AlertDialog.Builder(this).apply {
            setCancelable(cancellable)
            setView(view)

        }.create()

        val dialogWindow = dialog.window!!

        WindowManager.LayoutParams().apply {
            copyFrom(dialogWindow.attributes)
            width = WRAP_CONTENT
            height = WRAP_CONTENT
            dialogWindow.attributes = this
            dialogWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        dialog.setCanceledOnTouchOutside(cancellable)
        dialog.show()

        dialogWindow.setLayout(requestedWidth, requestedHeight)

        return dialog
    }

    object ServiceBuilder {
        private val client = OkHttpClient.Builder().build()

        private val retrofit = Retrofit.Builder()
            .baseUrl("http://api.server.com") // change this IP for testing by your actual machine IP
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        fun <T> buildService(service: Class<T>): T {
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