package com.example.bookify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenPDF extends AppCompatActivity {
    PDFView pdfView;
    ProgressBar progressBar;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_view);
        Intent intent = getIntent();
        final Book book = (Book) intent.getSerializableExtra("Bookpdf");
        Toast.makeText(this, book.getPdf_url(), Toast.LENGTH_SHORT).show();
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + book.getPdf_url());
        progressBar.setVisibility(View.GONE);




//        webView = findViewById(R.id.webview);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl(book.pdf_url);
//        progressBar.setVisibility(View.GONE);
        //gak bisa yang ini

        //TODO Load PDF from URL Here, url from book.pdf_url


    }
}