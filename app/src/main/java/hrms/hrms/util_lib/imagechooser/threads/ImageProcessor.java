package hrms.hrms.util_lib.imagechooser.threads;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import hrms.hrms.util_lib.imagechooser.api.ChosenImage;
import hrms.hrms.util_lib.imagechooser.api.FileUtils;
import hrms.hrms.util_lib.imagechooser.listeners.ImageChooserListener;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;

public class ImageProcessor extends Thread {
    private static final String TAG = "ImageProcessor";
    private ImageChooserListener listener;
    private String filePath;
    protected Context context;
    private String foldername;
    private boolean shouldCreateThumnails;
    private String mediaExtension;
    private boolean clearOldFiles = false;


    public ImageProcessor(String filePath, String foldername, boolean shouldCreateThumbnails) {
        this.setMediaExtension("jpg");
        this.filePath = filePath;
        this.foldername = foldername;
        this.shouldCreateThumnails = shouldCreateThumbnails;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private void setMediaExtension(String extension) {
        this.mediaExtension = extension;
    }

    public void clearOldFiles() {
        this.clearOldFiles = true;
    }

    private void downloadAndProcess(String url) throws Exception {
        this.filePath = this.downloadFile(url);
        this.process();
    }

    private String[] createThumbnails(String image) throws Exception {
        String[] images = new String[]{this.getThumnailPath(image), this.getThumbnailSmallPath(image)};
        return images;
    }

    private String getThumnailPath(String file) throws Exception {
        Log.i("MediaProcessorThread", "Compressing ... THUMBNAIL");
        return this.compressAndSaveImage(file, 1);
    }

    private String getThumbnailSmallPath(String file) throws Exception {
        Log.i("MediaProcessorThread", "Compressing ... THUMBNAIL SMALL");
        return this.compressAndSaveImage(file, 2);
    }

    private String compressAndSaveImage(String fileImage, int scale) throws Exception {
        try {
            ExifInterface e = new ExifInterface(fileImage);
            String width = e.getAttribute("ImageWidth");
            String length = e.getAttribute("ImageLength");
            int orientation = e.getAttributeInt("Orientation", 1);
            short rotate = 0;
            Log.i("MediaProcessorThread", "Before: " + width + "x" + length);
            switch (orientation) {
                case 3:
                    rotate = 180;
                case 4:
                case 5:
                case 7:
                default:
                    break;
                case 6:
                    rotate = 90;
                    break;
                case 8:
                    rotate = -90;
            }

            int w = Integer.parseInt(width);
            int l = Integer.parseInt(length);
            int what = w > l ? w : l;
            BitmapFactory.Options options = new BitmapFactory.Options();
            if (what > 1500) {
                options.inSampleSize = scale * 4;
            } else if (what > 1000 && what <= 1500) {
                options.inSampleSize = scale * 3;
            } else if (what > 400 && what <= 1000) {
                options.inSampleSize = scale * 2;
            } else {
                options.inSampleSize = scale;
            }

            Log.i("MediaProcessorThread", "Scale: " + what / options.inSampleSize);
            Log.i("MediaProcessorThread", "Rotate: " + rotate);
            Bitmap bitmap = BitmapFactory.decodeFile(fileImage, options);
            File original = new File(fileImage);
            File file = new File(original.getParent() + File.separator + original.getName().replace(".", "_fact_" + scale + "."));
            FileOutputStream stream = new FileOutputStream(file);
            if (rotate != 0) {
                Matrix exifAfter = new Matrix();
                exifAfter.setRotate((float) rotate);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), exifAfter, false);
            }

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            ExifInterface exifAfter1 = new ExifInterface(file.getAbsolutePath());
            String widthAfter = exifAfter1.getAttribute("ImageWidth");
            String lengthAfter = exifAfter1.getAttribute("ImageLength");
            Log.i("MediaProcessorThread", "After: " + widthAfter + "x" + lengthAfter);
            stream.flush();
            stream.close();
            return file.getAbsolutePath();
        } catch (IOException var19) {
            var19.printStackTrace();
            throw var19;
        } catch (Exception var20) {
            var20.printStackTrace();
            throw new Exception("Corrupt or deleted file???");
        }
    }

    private void copyFileToDir() throws Exception {
        try {
            File e = new File(Uri.parse(this.filePath).getPath());
            File copyTo = new File(FileUtils.getDirectory(this.foldername) + File.separator + e.getName());
            FileInputStream streamIn = new FileInputStream(e);
            BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(copyTo));
            byte[] buf = new byte[2048];

            int len;
            while ((len = streamIn.read(buf)) > 0) {
                outStream.write(buf, 0, len);
            }

            streamIn.close();
            outStream.close();
            this.filePath = copyTo.getAbsolutePath();
        } catch (FileNotFoundException var7) {
            var7.printStackTrace();
            throw new Exception("File not found");
        } catch (IOException var8) {
            var8.printStackTrace();
            throw var8;
        } catch (Exception var9) {
            var9.printStackTrace();
            throw new Exception("Corrupt or deleted file???");
        }
    }

    private String downloadFile(String url) {
        String localFilePath = "";

        try {
            URL urlObj = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
            InputStream stream = urlConnection.getInputStream();
            localFilePath = FileUtils.getDirectory(this.foldername) + File.separator + Calendar.getInstance().getTimeInMillis() + "." + this.mediaExtension;
            File localFile = new File(localFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(localFile);
            byte[] buffer = new byte[1024];

            int len;
            while ((len = stream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, len);
            }

            fileOutputStream.flush();
            fileOutputStream.close();
            stream.close();
            Log.i("MediaProcessorThread", "Image saved: " + localFilePath.toString());
        } catch (Exception var11) {
            var11.printStackTrace();
        }

        return localFilePath;
    }

    private void manageDiretoryCache(final String extension) {
        if (this.clearOldFiles) {
            File directory = null;
            directory = new File(FileUtils.getDirectory(this.foldername));
            File[] files = directory.listFiles();
            long count = 0L;
            if (files != null) {
                File[] filterFiles = files;
                int filter = files.length;

                for (int var7 = 0; var7 < filter; ++var7) {
                    File today = filterFiles[var7];
                    count += today.length();
                }

                Log.i("MediaProcessorThread", "Directory size: " + count);
                if (count > 524288000L) {
                    final long var15 = Calendar.getInstance().getTimeInMillis();
                    FileFilter var16 = new FileFilter() {
                        public boolean accept(File pathname) {
                            return var15 - pathname.lastModified() > 864000000L && pathname.getAbsolutePath().toUpperCase(Locale.ENGLISH).endsWith(extension.toUpperCase(Locale.ENGLISH));
                        }
                    };
                    filterFiles = directory.listFiles(var16);
                    int deletedFileCount = 0;
                    File[] var14 = filterFiles;
                    int var13 = filterFiles.length;

                    for (int var12 = 0; var12 < var13; ++var12) {
                        File file = var14[var12];
                        ++deletedFileCount;
                        file.delete();
                    }

                    Log.i("MediaProcessorThread", "Deleted " + deletedFileCount + " files");
                }

            }
        }
    }

    private void processPicasaMedia(String path, String extension) throws Exception {
        Log.i("MediaProcessorThread", "Picasa Started");

        try {
            InputStream e = this.context.getContentResolver().openInputStream(Uri.parse(path));
            this.filePath = FileUtils.getDirectory(this.foldername) + File.separator + Calendar.getInstance().getTimeInMillis() + extension;
            BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(this.filePath));
            byte[] buf = new byte[2048];

            while (true) {
                int len;
                if ((len = e.read(buf)) <= 0) {
                    e.close();
                    outStream.close();
                    this.process();
                    break;
                }

                outStream.write(buf, 0, len);
            }
        } catch (FileNotFoundException var7) {
            var7.printStackTrace();
            throw var7;
        } catch (Exception var8) {
            var8.printStackTrace();
            throw var8;
        }

        Log.i("MediaProcessorThread", "Picasa Done");
    }

    private void processGooglePhotosMedia(String path, String extension) throws Exception {
        Log.i("MediaProcessorThread", "Google photos Started");
        Log.i("MediaProcessorThread", "URI: " + path);
        Log.i("MediaProcessorThread", "Extension: " + extension);
        String retrievedExtension = this.checkExtension(Uri.parse(path));
        if (retrievedExtension != null && !TextUtils.isEmpty(retrievedExtension)) {
            extension = "." + retrievedExtension;
        }

        try {
            this.filePath = FileUtils.getDirectory(this.foldername) + File.separator + Calendar.getInstance().getTimeInMillis() + extension;
            ParcelFileDescriptor e = this.context.getContentResolver().openFileDescriptor(Uri.parse(path), "r");
            FileDescriptor fileDescriptor = e.getFileDescriptor();
            FileInputStream inputStream = new FileInputStream(fileDescriptor);
            BufferedInputStream reader = new BufferedInputStream(inputStream);
            BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(this.filePath));
            byte[] buf = new byte[2048];

            while (true) {
                int len;
                if ((len = reader.read(buf)) <= 0) {
                    outStream.flush();
                    outStream.close();
                    inputStream.close();
                    this.process();
                    break;
                }

                outStream.write(buf, 0, len);
            }
        } catch (FileNotFoundException var11) {
            var11.printStackTrace();
            throw var11;
        } catch (Exception var12) {
            var12.printStackTrace();
            throw var12;
        }

        Log.i("MediaProcessorThread", "Picasa Done");
    }

    private String checkExtension(Uri uri) {
        String extension = "";
        Cursor cursor = this.context.getContentResolver().query(uri, (String[]) null, (String) null, (String[]) null, (String) null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                String displayName = cursor.getString(cursor.getColumnIndex("_display_name"));
                int position = displayName.indexOf(".");
                extension = displayName.substring(position + 1);
                Log.i("MediaProcessorThread", "Display Name: " + displayName);
                int sizeIndex = cursor.getColumnIndex("_size");
                String size = null;
                if (!cursor.isNull(sizeIndex)) {
                    size = cursor.getString(sizeIndex);
                } else {
                    size = "Unknown";
                }

                Log.i("MediaProcessorThread", "Size: " + size);
            }
        } finally {
            cursor.close();
        }

        return extension;
    }

    @SuppressLint({"NewApi"})
    private String getAbsoluteImagePathFromUri(Uri imageUri) {
        String[] proj = new String[]{"_data", "_display_name"};
        Log.i("MediaProcessorThread", "Image Uri: " + imageUri.toString());
        if (imageUri.toString().startsWith("content://com.android.gallery3d.provider")) {
            imageUri = Uri.parse(imageUri.toString().replace("com.android.gallery3d", "com.google.android.gallery3d"));
        }

        String filePath = "";
        String imageUriString = imageUri.toString();
        if (!imageUriString.startsWith("content://com.google.android.gallery3d") && !imageUriString.startsWith("content://com.google.android.apps.photos.content") && !imageUriString.startsWith("content://com.android.providers.media.documents") && !imageUriString.startsWith("content://com.google.android.apps.docs.storage") && !imageUriString.startsWith("content://com.microsoft.skydrive.content.external")) {
            Cursor cursor = this.context.getContentResolver().query(imageUri, proj, (String) null, (String[]) null, (String) null);
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
            cursor.close();
        } else {
            filePath = imageUri.toString();
        }

        if (filePath == null && isDownloadsDocument(imageUri) && Build.VERSION.SDK_INT >= 19) {
            filePath = getPath(this.context, imageUri);
        }

        return filePath;
    }

    @TargetApi(19)
    private static String getPath(Context context, Uri uri) {
        boolean isKitKat = Build.VERSION.SDK_INT >= 19;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            String docId;
            String[] split;
            String type;
            if (isExternalStorageDocument(uri)) {
                docId = DocumentsContract.getDocumentId(uri);
                split = docId.split(":");
                type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else {
                if (isDownloadsDocument(uri)) {
                    docId = DocumentsContract.getDocumentId(uri);
                    Uri split1 = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId).longValue());
                    return getDataColumn(context, split1, (String) null, (String[]) null);
                }

                if (isMediaDocument(uri)) {
                    docId = DocumentsContract.getDocumentId(uri);
                    split = docId.split(":");
                    type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    String selection = "_id=?";
                    String[] selectionArgs = new String[]{split[1]};
                    return getDataColumn(context, contentUri, "_id=?", selectionArgs);
                }
            }
        } else {
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, (String) null, (String[]) null);
            }

            if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }

        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = "_data";
        String[] projection = new String[]{"_data"};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, (String) null);
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow("_data");
                String var9 = cursor.getString(column_index);
                return var9;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }

        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    public void setListener(ImageChooserListener listener) {
        this.listener = listener;
    }

    public void run() {
        try {
            this.manageDiretoryCache("jpg");
            this.processImage();
        } catch (IOException var2) {
            var2.printStackTrace();
            if (this.listener != null) {
                this.listener.onError(var2.getMessage());
            }
        } catch (Exception var3) {
            var3.printStackTrace();
            if (this.listener != null) {
                this.listener.onError(var3.getMessage());
            }
        }

    }

    private void processImage() throws Exception {
        Log.i("ImageProcessorThread", "Processing Image File: " + this.filePath);
        if (this.filePath != null && this.filePath.startsWith("content:")) {
            this.filePath = this.getAbsoluteImagePathFromUri(Uri.parse(this.filePath));
        }

        if (this.filePath != null && !TextUtils.isEmpty(this.filePath)) {
            if (this.filePath.startsWith("http")) {
                this.downloadAndProcess(this.filePath);
            } else if (!this.filePath.startsWith("content://com.google.android.gallery3d") && !this.filePath.startsWith("content://com.microsoft.skydrive.content")) {
                if (!this.filePath.startsWith("content://com.google.android.apps.photos.content") && !this.filePath.startsWith("content://com.android.providers.media.documents") && !this.filePath.startsWith("content://com.google.android.apps.docs.storage")) {
                    this.process();
                } else {
                    this.processGooglePhotosMedia(this.filePath, ".jpg");
                }
            } else {
                this.processPicasaMedia(this.filePath, ".jpg");
            }
        } else if (this.listener != null) {
            this.listener.onError("Couldn\'t process a null file");
        }

    }

    private void process() throws IOException, Exception {
        if (!this.filePath.contains(this.foldername)) {
            this.copyFileToDir();
        }

        if (this.shouldCreateThumnails) {
            String[] thumbnails = this.createThumbnails(this.filePath);
            this.processingDone(this.filePath, thumbnails[0], thumbnails[1]);
        } else {
            this.processingDone(this.filePath, this.filePath, this.filePath);
        }

    }

    private void processingDone(String original, String thumbnail, String thunbnailSmall) {
        if (this.listener != null) {
            ChosenImage image = new ChosenImage();
            image.setFilePathOriginal(original);
            this.listener.onImageChosen(image);
        }

    }
}
