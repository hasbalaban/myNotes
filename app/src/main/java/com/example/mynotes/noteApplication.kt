package com.example.mynotes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
//for hilt we have to create this class with this annotatin
@HiltAndroidApp
class noteApplication:Application() {
}