# ImageViewFromFile
Android Kotlin example to download an image from URL, store it in Pictures folder and show it using imageView

Setting up the project
======================
In Android Studio create a new project from template "Basic Activity (Material3)".

Disabling second fragment
=========================
For our example we need only one fragment - so disable the "Next" button.

In `fragment_first.xml` update the sections `<Button ...>` and `<TextView ...>` to
```xml
        <Button
            android:id="@+id/btnUpdateImage"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/update_image"
            app:layout_constraintBottom_toTopOf="@id/imageView"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <ImageView
            android:id="@+id/imageView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:contentDescription="@string/my_image_caption"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/btnUpdateImage" />
```
Don't forget to create a string value resources for `update_image` and `my_image_caption`.

Since the button does not exist anymore, we need to remove the corresponding code from `FirstFragment.kt`:
```kotlin
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/
    }
```

Let's give a resonable headline to our screen by renaming the `first_fragment_label` which occurs in `nav_graph.xml`:
```kotlin
    <fragment
        android:id="@+id/FirstFragment"
        android:name="com.sesko.imageviewfromfile.FirstFragment"
        android:label="@string/first_fragment_label"
        tools:layout="@layout/fragment_first">

        <action
            android:id="@+id/action_FirstFragment_to_SecondFragment"
            app:destination="@id/SecondFragment" />
    </fragment>
```
For this we need to edit `strings.xml` and choose the text "View downloaded image"

Adding the dialog to enter URL of image file to download
========================================================
Changing the icon of the floating button
----------------------------------------
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

Adding the dialog
-----------------
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

Linking the dialog with the floating button
-------------------------------------------
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

Adding the permissions to access the Internet and to write to the android file system
=====================================================================================
Add permissions to the Manifest
-------------------------------
Add the following lines to the `AndroidManifest.xml` after `<manifest .../>`:
```xml
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

Ask for permissions with older Android systems
----------------------------------------------
In Android 9 (API level 28) and lower, you must ask the user for permission.

Create a companion object in `MainActivity.kt` to store 
```kotlin
    companion object {
        private const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
    }
```

Add the function to ask for permissions:
```kotlin
    @TargetApi(Build.VERSION_CODES.M)
    fun askPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                AlertDialog.Builder(this)
                    .setTitle("Permission required")
                    .setMessage("Permission required to download file.")
                    .setPositiveButton("Accept") { dialog, id ->
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                        )
                        finish()
                    }
                    .setNegativeButton("Deny") { dialog, id -> dialog.cancel() }
                    .show()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
                )
            }
        }
    }
```

And add this at the end of the `onCreate` function in `MainActivity.kt`:
```kotlin
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            askPermissions()
        }
```

Downloading the file
====================
In the beginning von `MainActivity.kt` we add two variables:
```kotlin
    var msg: String? = ""
    var lastMsg = ""
```

and we add another companion object to store the filename with path of the downloaded image
```kotlin
    companion object {
        private const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
        var localImageFile: File? = null
    }
```

Using the Android build-in download manager
------------------------------------------
This is the function to use the Android downloader to download the file:
```kotlin
    private fun downloadFile(downloadUri: Uri, dirType: String, subPathFile: File) {
        val downloadManager = this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(downloadUri).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(subPathFile.toString())
                .setDescription("")
                .setDestinationInExternalPublicDir(
                    dirType,
                    subPathFile.toString()
                )
        }

        val downloadId = downloadManager.enqueue(request)
        val query = DownloadManager.Query().setFilterById(downloadId)
        Thread(Runnable {
            var downloading = true
            while (downloading) {
                val cursor: Cursor = downloadManager.query(query)
                cursor.moveToFirst()
                if (cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                    downloading = false
                }
                val status =
                    cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))
                msg = statusMessage(downloadUri.toString(), subPathFile, status)
                if (msg != lastMsg) {
                    this.runOnUiThread {
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                    }
                    lastMsg = msg ?: ""
                }
                cursor.close()
            }
        }).start()
    }

    private fun statusMessage(url: String, directory: File, status: Int): String? {
        var msg = ""
        msg = when (status) {
            DownloadManager.STATUS_FAILED -> "failed"
            DownloadManager.STATUS_PAUSED -> "paused"
            DownloadManager.STATUS_PENDING -> "pending"
            DownloadManager.STATUS_RUNNING -> "downloading"
            DownloadManager.STATUS_SUCCESSFUL -> "done"
            else -> "error"
        }
        return msg
    }
```

The download function is called from a downloadWrapper:
```kotlin
    private fun downloadWrapper(url: String) {
        if (url == "") {
            return
        }

        val dirType = Environment.DIRECTORY_PICTURES
        val subPath = getString(R.string.app_name)

        val directory = File(Environment.getExternalStoragePublicDirectory(dirType), subPath)
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val downloadUri = Uri.parse(url)
        val fileName = url.substring(url.lastIndexOf("/") + 1)
        val localFile = File(directory, fileName)
        val subPathFile = File(subPath, fileName)
        if (!localFile.exists()) {
            downloadFile(downloadUri, dirType, subPathFile)
        }
    }
```

Change the `Toast` in `enterDownloadUrl(view: View)` to calling the download function:
```kotlin
    fun enterDownloadUrl(view: View) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Enter Url to image file")
        val dialogLayout = inflater.inflate(R.layout.enter_download_url, null)
        val downloadUrl  = dialogLayout.findViewById<EditText>(R.id.downloadUrl)
        builder.setView(dialogLayout)
        builder.setPositiveButton("Download") {
                dialogInterface, i -> downloadWrapper(downloadUrl.text.toString())
        }
        builder.show()
    }
```

Add Yes/No dialog box
---------------------
Let's add a dialog box to ask for permission to download even of the local file exists.
In the downloadWrapper function, add the following `else {...}` condition to `if (!localFile.exists()) {...}`:
```kotlin
        if (!localFile.exists()) {
            downloadFile(downloadUri, dirType, subPathFile)
        } else {
            // open Dialog and ask to overwrite the file
            val dialogClickListener: DialogInterface.OnClickListener =
                DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            downloadFile(downloadUri, dirType, subPathFile)
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                        }
                    }
                }

            val builder = AlertDialog.Builder(this)
            builder.setMessage("File already exists. Do you want to download again?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show()
            return
        }
```

Displaying the image
====================
We want to display the image when the Update button is pressed.

This works within the `FirstFagment.kt`.

Add the function to display the image
-------------------------------------
```kotlin
    private fun setupImageView() {
        binding.btnUpdateImage.setOnClickListener {
            if (localImageFile != null) {
                println(">>> $localImageFile")
                val bitmap = BitmapFactory.decodeFile(localImageFile!!.path)
                binding.imageView.setImageBitmap(bitmap)
            }
        }
    }
```

Bind the function call to the update button
-------------------------------------------
There is already the function `onViewCreated` where we simply add the function call:
```kotlin
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/
        
        setupImageView()
    }
```

That's it.
