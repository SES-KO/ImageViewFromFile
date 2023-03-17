# ImageViewFromFile
Android Kotlin example to download an image from URL, store it in Pictures folder and show it using imageView

Setting up the project
======================
In Android Studio create a new project from template "Basic Activity (Material3)".

Adding the download function
============================

Changing the icon
-----------------
At first we exchange the "mail" icon to a download icon from material design icons db.

In the "Project" browser, right-click on "App->New->Vector Asset".
Click on "Clip art", enter "download" in the search field and select the "file download" icon and clock "Next" and "Finish".

Change the icon name as follows:
..  code-block:: kotlin
    :caption: res/layout/activity_main.xml
    
        <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_marginEnd="@dimen/fab_margin"
        android:layout_marginBottom="16dp"
        app:srcCompat="@drawable/baseline_file_download_24" />


This project is still WORK-IN-PROGRESS.
