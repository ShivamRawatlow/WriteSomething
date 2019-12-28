package com.thelegendofawizard.writesomething.ui.googlesignin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import com.thelegendofawizard.writesomething.MainActivity
import com.thelegendofawizard.writesomething.PersonDetail
import com.thelegendofawizard.writesomething.R
import com.thelegendofawizard.writesomething.utils.toast
import kotlinx.android.synthetic.main.activity_google_sign_in.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class GoogleSignInActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val googleSignInViewModelFactory :GoogleSignInViewModelFactory by instance()
    private lateinit var googleSignInViewModel: GoogleSignInViewModel

    val RC_SIGN_IN: Int = 1
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var googleSignInOptions: GoogleSignInOptions


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_sign_in)

        googleSignInViewModel =  ViewModelProvider(this, googleSignInViewModelFactory)
            .get(GoogleSignInViewModel::class.java)

        configureGoogleSignIn()
        setupUI()

        myclosegooglesignin_imageButton.setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    /* override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {

            val action =
                GoogleSingInDirections.actionGoogleSingInToHomeFragment()
            view?.findNavController()?.navigate(action)

        }
    }*/

    private fun configureGoogleSignIn() {
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(applicationContext, googleSignInOptions)
    }

    private fun setupUI() {
        mygoogleSignInButton.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let {
                    firebaseAuthWithGoogle(it)
                }

            } catch (e: ApiException) {
                applicationContext.toast("Google sign in failed:(")
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        googleSignInViewModel.getFirebaseAuthInstance()
            .signInWithCredential(credential).addOnCompleteListener {

                if (it.isSuccessful) {
                    if(it.result?.additionalUserInfo!!.isNewUser){

                        val user = googleSignInViewModel.getCurrentUser()
                        val userEmail = user?.email.toString()
                        val userName = user?.displayName.toString()
                        val person =
                            PersonDetail(
                                userEmail,
                                userName
                            )
                        googleSignInViewModel.saveUser(person)
                    }

                    val intent = Intent(applicationContext,MainActivity::class.java)
                    startActivity(intent)
                    finish()

                    // startActivity(HomeActivity.getLaunchIntent(this))
                } else {
                    applicationContext.toast("Google sign in failed:(")
                }
            }
    }
}
