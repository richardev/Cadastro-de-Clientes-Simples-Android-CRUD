package com.example.cadastrodeclientes;

import android.support.v7.app.ActionBarActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Inserir extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inserir);
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
	
	public void CadastrarClick(View v){
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
			ctv.put("email", txtMail.getText().toString());
			
			if(db.insert("clientes", "id", ctv) > 0){
				Toast.makeText(getBaseContext(),"Sucesso ao cadastrar", Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(getBaseContext(),"Erro ao cadastrar", Toast.LENGTH_SHORT).show();
			}
			} catch(Exception ex){
				Toast.makeText(getBaseContext(),ex.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
	}
}
