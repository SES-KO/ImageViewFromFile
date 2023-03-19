# ImageViewFromFile
Android Kotlin example to download an image from URL, store it in Pictures folder and show it using imageView

Setting up the project
======================
In Android Studio create a new project from template "Basic Activity (Material3)".

Disabling second fragment
=========================
For our example we need only one fragment - so disable the "Next" button.

In `fragment_first.xml` remove the section `<Button ...>`

and change the whole section `<TextView ...>` to
```xml
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
    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }*/
```

Adding the dialog to enter URL of image file to download
========================================================

Changing the icon
-----------------
At first we exchange the "mail" icon to a download icon from material design icons db.

In the "Project" browser, right-click and choose "New->Vector Asset".
Click on "Clip art", enter "download" in the search field and select the "file download" icon and click "Next" and "Finish".

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

Adding dialog
-------------
In the "Project" browser, right-click and choose "New->XML->Layout XML File" and name it "enter_download_url".

In new created `enter_download_url.xml` add
```xml
    <EditText
        android:id="@+id/downloadUrl"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="http://..."/>
```

Add the following function to `MainActivity.kt`:
```kotlin
    fun enterDownloadUrl(view: View) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Enter Url to image file")
        val dialogLayout = inflater.inflate(R.layout.enter_download_url, null)
        val downloadUrl  = dialogLayout.findViewById<EditText>(R.id.downloadUrl)
        builder.setView(dialogLayout)
        builder.setPositiveButton("Download") {
                dialogInterface, i -> Toast.makeText(applicationContext, "Downloading " + downloadUrl.text.toString(), Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }
```

Linking the dialog with the button
----------------------------------
First we need to disable the existing action in `MainActivity.kt`:
```kotlin
        /*binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.fab)
                .setAction("Action", null).show()
        }*/
```

Link floating action button to the new added function directly in the `activity_main.xml`:
```kotlin
    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_marginEnd="@dimen/fab_margin"
        android:layout_marginBottom="16dp"
        android:onClick="enterDownloadUrl"
        app:srcCompat="@drawable/baseline_login_24" />

```


This project is still WORK-IN-PROGRESS.
