package com.thelegendofawizard.writesomething
import android.app.Application
import com.thelegendofawizard.writesomething.ui.googlesignin.FirebaseAuthSource
import com.thelegendofawizard.writesomething.ui.googlesignin.GoogleSignInViewModelFactory
import com.thelegendofawizard.writesomething.roomdatabase.LocalDatabase
import com.thelegendofawizard.writesomething.roomdatabase.LocalDatabaseDao
import com.thelegendofawizard.writesomething.roomdatabase.PicsDatabaseDao
import com.thelegendofawizard.writesomething.ui.addnote.AddNoteViewModelFactory
import com.thelegendofawizard.writesomething.ui.home.HomeViewModelFactory
import com.thelegendofawizard.writesomething.ui.showmynotes.ShowMyNotesViewModelFactory
import com.thelegendofawizard.writesomething.ui.members.MembersViewModelFactory
import com.thelegendofawizard.writesomething.ui.myprofile.MyProfileViewModelFactory
import com.thelegendofawizard.writesomething.ui.showmembernotes.ShowMemberNotesViewModelFactory
import com.thelegendofawizard.writesomething.ui.userprofile.UserProfileViewModelFactory

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class WriteSomethingApplication:Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@WriteSomethingApplication))


        bind<LocalDatabase>() with singleton { LocalDatabase.invoke(instance()) }
        bind<LocalDatabaseDao>() with singleton { instance<LocalDatabase>().localDatabaseDao() }
        bind<PicsDatabaseDao>() with singleton { instance<LocalDatabase>().picsDatabaseDao() }


        bind<FirebaseDatabaseSource>() with singleton { FirebaseDatabaseSource() }
        bind<FirebaseAuthSource>() with singleton { FirebaseAuthSource() }
        bind<FirebaseStorageSource>() with singleton { FirebaseStorageSource() }

        bind<Repository>() with singleton { Repository(instance(),instance(),instance(),instance(),instance()) }


        bind<GoogleSignInViewModelFactory>() with provider { GoogleSignInViewModelFactory(instance() ,instance()) }
        bind<MembersViewModelFactory>() with provider { MembersViewModelFactory(instance(),instance()) }
        bind<MyProfileViewModelFactory>() with provider { MyProfileViewModelFactory(instance(),instance()) }
        bind<UserProfileViewModelFactory>() with provider { UserProfileViewModelFactory(instance(),instance()) }
       // bind<OpenNoteViewModelFactory>() with provider { OpenNoteViewModelFactory(instance(),instance()) }
        bind<ShowMyNotesViewModelFactory>() with provider { ShowMyNotesViewModelFactory(instance(),instance()) }
        bind<AddNoteViewModelFactory>() with provider{AddNoteViewModelFactory(instance(),instance())}
        bind<HomeViewModelFactory>() with provider { HomeViewModelFactory(instance(),instance()) }
        bind<ShowMemberNotesViewModelFactory>() with provider { ShowMemberNotesViewModelFactory(instance(),instance()) }
    }
}

