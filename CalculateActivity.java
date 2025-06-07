package com.example.waterchecker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/** @noinspection CallToPrintStackTrace*/
public class CalculateActivity extends AppCompatActivity {

    private String selectedPH = "6.5", selectedNTU = "0.1", selectedChlorine = "0.2";
    private static final int STORAGE_PERMISSION_CODE = 100;
    private TextView txtResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        Spinner spinnerPH = findViewById(R.id.spinnerPH);
        Spinner spinnerNTU = findViewById(R.id.spinnerNTU);
        Spinner spinnerChlorine = findViewById(R.id.spinnerChlorine);
        Button btnDownload = findViewById(R.id.btnDownload);
        txtResult = findViewById(R.id.txtResult); //

        // Set Spinner Values
        setSpinnerAdapter(spinnerPH, new String[]{"6.5", "7.0", "7.5", "8.0"});
        setSpinnerAdapter(spinnerNTU, new String[]{"0.1", "0.5", "1.0", "5.0"});
        setSpinnerAdapter(spinnerChlorine, new String[]{"0.2", "0.5", "1.0", "2.0"});

        // Set Listeners
        setSpinnerListener(spinnerPH, "PH");
        setSpinnerListener(spinnerNTU, "NTU");
        setSpinnerListener(spinnerChlorine, "Chlorine");

        // Download Button Click
        btnDownload.setOnClickListener(v -> {
            calculateResult();
            if (checkStoragePermission()) {
                generatePDF();
            } else {
                requestStoragePermission();
            }
        });

        Button btnread = findViewById(R.id.btnread);

// Read Button Click
        btnread.setOnClickListener(v -> {
            String filePath = getString(R.string.res_assest_home_html);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(filePath));
            startActivity(intent);
        });
    }

    private void setSpinnerAdapter(Spinner spinner, String[] values) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, values);
        spinner.setAdapter(adapter);
    }

    private void setSpinnerListener(Spinner spinner, final String type) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                switch (type) {
                    case "PH":
                        selectedPH = value;
                        break;
                    case "NTU":
                        selectedNTU = value;
                        break;
                    case "Chlorine":
                        selectedChlorine = value;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @SuppressLint("SetTextI18n")
    private void calculateResult() {

        double ph = Double.parseDouble(selectedPH);
        double ntu = Double.parseDouble(selectedNTU);
        double chlorine = Double.parseDouble(selectedChlorine);

        String result;
        if (ph >= 6.5 && ph <= 7.5 && ntu <= 1.0 && chlorine >= 0.2 && chlorine <= 1.0) {
            result = "Safe for Drinking";
        } else {
            result = "Not Safe for Drinking";
        }

        txtResult.setText("Result: " + result);
    }

    private void generatePDF() {
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/WaterQualityReport.pdf";

        try {
            File file = new File(filePath);
            PdfWriter writer = new PdfWriter(new FileOutputStream(file));
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Water Quality Report"));
            document.add(new Paragraph("----------------------"));
            document.add(new Paragraph("PH Level: " + selectedPH));
            document.add(new Paragraph("NTU Level: " + selectedNTU));
            document.add(new Paragraph("Chlorine Level: " + selectedChlorine));
            document.add(new Paragraph("----------------------"));


            String resultText = txtResult.getText().toString();
            document.add(new Paragraph(resultText));

            document.add(new Paragraph("----------------------"));
            document.add(new Paragraph("Checked using Water Quality Checker App"));

            document.close();
            Toast.makeText(this, "PDF Saved: " + filePath, Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                generatePDF();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
