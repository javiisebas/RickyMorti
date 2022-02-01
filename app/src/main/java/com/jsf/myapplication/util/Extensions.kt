package com.jsf.myapplication.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

// Crea una función de extensión para el ViewGroup
fun ViewGroup.inflate(resource: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(resource, this, attachToRoot)