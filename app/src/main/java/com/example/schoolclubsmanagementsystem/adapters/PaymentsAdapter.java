package com.example.schoolclubsmanagementsystem.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolclubsmanagementsystem.R;
import com.example.schoolclubsmanagementsystem.models.Payment;

import java.util.List;

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.PaymentViewHolder> {

    private Context context;
    private List<Payment> paymentsList;

    public PaymentsAdapter(Context context, List<Payment> paymentsList) {
        this.context = context;
        this.paymentsList = paymentsList;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_payment, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        Payment payment = paymentsList.get(position);
        holder.paymentAmountTextView.setText(String.valueOf(payment.getAmount())); // Using getAmount() method
        holder.paymentDateTextView.setText(payment.getDate());
        holder.paymentDescriptionTextView.setText(payment.getDescription());
    }

    @Override
    public int getItemCount() {
        return paymentsList.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder {
        TextView paymentAmountTextView;
        TextView paymentDateTextView;
        TextView paymentDescriptionTextView;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            paymentAmountTextView = itemView.findViewById(R.id.payment_amount_text_view);
            paymentDateTextView = itemView.findViewById(R.id.payment_date_text_view);
            paymentDescriptionTextView = itemView.findViewById(R.id.payment_description_text_view);
        }
    }
}
