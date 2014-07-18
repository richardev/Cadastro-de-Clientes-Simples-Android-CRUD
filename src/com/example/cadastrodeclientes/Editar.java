package com.example.cadastrodeclientes;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Editar extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editar);
		
		Intent it = getIntent();
		
		int id = it.getIntExtra("id", 0);
		
		SQLiteDatabase db = openOrCreateDatabase("clientes.db", Context.MODE_PRIVATE, null);
		
		Cursor cursor = db.rawQuery("SELECT * FROM clientes WHERE _id = ?", new String[]{String.valueOf(id)});
		
		if(cursor.moveToFirst()){
			EditText txtNome = (EditText)findViewById(R.id.txtNome);
			EditText txtMail = (EditText)findViewById(R.id.txtMail);
			
			txtNome.setText(cursor.getString(1));
			txtMail.setText(cursor.getString(2));		
		}		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void AtualizarClick(View v){
		EditText txtNome = (EditText)findViewById(R.id.txtNome);
		EditText txtMail = (EditText)findViewById(R.id.txtMail);
		
		if(txtNome.getText().toString().length() <= 0){
			txtNome.setError("Preencha o campo nome.");
			txtNome.requestFocus();			
		} else if(txtMail.getText().toString().length() <= 0){
			txtMail.setError("Preencha o campo e-mail.");
			txtMail.requestFocus();	
		} else {
			try{
			SQLiteDatabase db = openOrCreateDatabase("clientes.db", Context.MODE_PRIVATE, null);
			
			ContentValues ctv = new ContentValues();
			ctv.put("nome", txtNome.getText().toString());
			ctv.put("email",  txtMail.getText().toString());
			
			Intent it = getIntent();	
			int id = it.getIntExtra("id", 0);
			
			if(db.update("clientes", ctv, "_id=?", new String[]{String.valueOf(id)}) > 0){
				Toast.makeText(getBaseContext(),"Sucesso ao atualizar", Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(getBaseContext(),"Erro ao atualizar", Toast.LENGTH_SHORT).show();
			}			
			} catch(Exception ex){
				Toast.makeText(getBaseContext(),ex.getMessage(), Toast.LENGTH_SHORT).show();
			}			
		}
	}
	public void ApagarClick(View v){		
			try{
			final SQLiteDatabase db = openOrCreateDatabase("clientes.db", Context.MODE_PRIVATE, null);
			
			Intent it = getIntent();	
			final int id = it.getIntExtra("id", 0);
			
			Builder msg = new Builder(Editar.this);
			msg.setMessage("Deseja apagar esse cliente?");
			msg.setNegativeButton("Não", null);
			msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(db.delete("clientes", "_id=?", new String[]{String.valueOf(id)}) > 0){
						Toast.makeText(getBaseContext(),"Sucesso ao apagar", Toast.LENGTH_SHORT).show();
						finish();
						db.close();
					} else {
						Toast.makeText(getBaseContext(),"Erro ao apagar", Toast.LENGTH_SHORT).show();
					}					
				}
			});
			
			msg.show();
						
			} catch(Exception ex){
				Toast.makeText(getBaseContext(),ex.getMessage(), Toast.LENGTH_SHORT).show();
			}		
		}
}