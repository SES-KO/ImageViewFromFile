# ImageViewFromFile
Android Kotlin example to download an image from URL, store it in Pictures folder and show it using imageView

Setting up the project
======================
In Android Studio create a new project from template "Basic Activity (Material3)".

Disabling second fragment
=========================
For our example we need only one fragment - so disable the "Next" button.

In `fragment_first.xml` remove these lines:
```xml
        <Button
            android:id="@+id/button_first"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/next"
            app:layout_constraintBottom_toTopOf="@id/textview_first"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
        />
```
and change the whole section `<TextView ...>` to
```
        <ImageView
            android:id="@+id/imageView"
            android:layout_width="80dp"
            android:layout_height="80dp"
            android:contentDescription="@string/my_image_caption"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent" />
```

Don't forget to create a string value resource for `my_image_caption`.

Since the button does not exist anymore, we need to remove the corresponding code from `FirstFragment.kt`:
```kotlin
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
```

Adding the download function
============================

Changing the icon
-----------------
At first we exchange the "mail" icon to a download icon from material design icons db.

In the "Project" browser, right-click on "App->New->Vector Asset".
Click on "Clip art", enter "download" in the search field and select the "file download" icon and clock "Next" and "Finish".

Change the icon name as follows in `res/layout/activity_main.xml`:
```xml
    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_marginEnd="@dimen/fab_margin"
        android:layout_marginBottom="16dp"
        app:srcCompat="@drawable/baseline_file_download_24" />
```

This project is still WORK-IN-PROGRESS.
