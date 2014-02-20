package com.anz.pdfviewer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Handles the opening of PDF files, using whatever app the user has installed to view PDFs
 * If no PDF app installed, shows a warning and directs them to appropriate Store listing
 */
public class PdfHandler {

    public PdfHandler(Context context) {
        this.context = context;
    }

    public void openPdf(String filename) {
        if (isPdfAppAvailable()) {
            copyPdfAndOpenIt(filename);
        } else {
            showPdfWarning();
        }
    }

    private void copyPdfAndOpenIt(String filename) {
        try {
            File file = copyPdfFromAssetsToStorage(filename);
            startPdfIntent(file);
        } catch (Exception e) {
            Log.e("PdfHandler", "Error handling the PDF file", e);
        }
    }

    private File copyPdfFromAssetsToStorage(String filename) throws Exception {
        String tempFilename = "temp.pdf";
        AssetManager is = context.getAssets();
        InputStream inputStream = is.open(filename);
        String outFilename = context.getFilesDir() + "/" + tempFilename;
        FileOutputStream outputStream = context.openFileOutput(tempFilename, Context.MODE_WORLD_READABLE);
        copy(inputStream, outputStream);
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        return new File(outFilename);
    }

    private void copy(InputStream fis, FileOutputStream fos) throws IOException {
        byte[] b = new byte[8];
        int i;
        while ((i = fis.read(b)) != -1) {
            fos.write(b, 0, i);
        }
    }

    private boolean isPdfAppAvailable() {
        PackageManager packageManager = context.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void startPdfIntent(File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        context.startActivity(intent);
    }

    private void showPdfWarning() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Please install an app to view PDF file");
        builder.setCancelable(false);
        builder.setPositiveButton("Install", getButtonListener());
        builder.setNegativeButton("Cancel", null);
        builder.setTitle("PDF Viewer");
        AlertDialog alert = builder.create();
        alert.show();
    }

    private DialogInterface.OnClickListener getButtonListener() {
        return new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    goToGooglePlayStoreEntry();
                }
            };
    }

    private void goToGooglePlayStoreEntry() {
        context.startActivity(getAppListingIntent());
    }

    private Intent getAppListingIntent() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String pdfAppPackageName = "com.adobe.reader";
        intent.setData(Uri.parse("market://details?id=" + pdfAppPackageName));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    private Context context;

}