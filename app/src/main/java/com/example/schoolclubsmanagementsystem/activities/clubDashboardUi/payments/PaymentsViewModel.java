package com.example.schoolclubsmanagementsystem.activities.clubDashboardUi.payments;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.schoolclubsmanagementsystem.firestore.Payments;
import com.example.schoolclubsmanagementsystem.models.Payment;

import java.util.List;

public class PaymentsViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Payment>> payments = new MutableLiveData<>();
    private final Payments paymentsDb;

    public PaymentsViewModel(@NonNull Application application) {
        super(application);
        paymentsDb = new Payments();
    }

    public LiveData<List<Payment>> getPayments() {
        return payments;
    }

    public void loadPayments(String clubId) {
        if (clubId != null && !clubId.isEmpty()) {
            paymentsDb.getPayments(clubId, new Payments.FirestoreCallback<List<Payment>>() {
                @Override
                public void onSuccess(List<Payment> result) {
                    payments.setValue(result);
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getApplication(), "Failed to load payments: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
