package com.michaelcorral.ortimer.di.modules

import com.michaelcorral.ortimer.screens.mainscreen.MainScreenActivity
import com.michaelcorral.ortimer.screens.mainscreen.MainScreenContract
import com.michaelcorral.ortimer.screens.mainscreen.MainScreenPresenter
import com.michaelcorral.ortimer.screens.mainscreen.dialogs.addtimeentry.MainScreenDialogAddTimeEntryContract
import com.michaelcorral.ortimer.screens.mainscreen.dialogs.addtimeentry.MainScreenDialogAddTimeEntryPresenter
import com.michaelcorral.ortimer.screens.mainscreen.dialogs.edittimeentry.MainScreenDialogEditTimeEntryContract
import com.michaelcorral.ortimer.screens.mainscreen.dialogs.edittimeentry.MainScreenDialogEditTimeEntryPresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainScreenModule = module {

    scope(named<MainScreenActivity>()) {
        scoped<MainScreenContract.Presenter> { (view: MainScreenContract.View) ->
            MainScreenPresenter(view, get())
        }
    }

    factory<MainScreenDialogAddTimeEntryContract.Presenter> { (view: MainScreenDialogAddTimeEntryContract.View) ->
        MainScreenDialogAddTimeEntryPresenter(view, get())
    }

    factory<MainScreenDialogEditTimeEntryContract.Presenter> { (view: MainScreenDialogEditTimeEntryContract.View) ->
        MainScreenDialogEditTimeEntryPresenter(view, get())
    }
}