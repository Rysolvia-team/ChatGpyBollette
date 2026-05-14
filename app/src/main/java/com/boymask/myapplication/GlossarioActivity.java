package com.boymask.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boymask.myapplication.listaparametri.RowModel;
import com.boymask.myapplication.listaparametri.TableAdapter;

import java.util.ArrayList;

public class GlossarioActivity extends AppCompatActivity {
    private ArrayList<RowModel> data = new ArrayList<>();
    private Button esci;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_glossario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        esci = findViewById(R.id.esci);
        recyclerView = findViewById(R.id.recyclerView);

        esci.setOnClickListener(v -> {

            finish();

        });

        data.clear();
        String value = loadText();

        data.add(new RowModel(value, ""));

        TableAdapter adapter = new TableAdapter(data);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private String loadText() {
        String val = "Accisa\n" +
                "\n" +
                "È un’imposta stabilita dallo Stato che si applica ai consumi di energia elettrica o gas.\n" +
                "\n" +
                "\n" +
                "Addebito diretto\n" +
                "\n" +
                "È il pagamento automatico della bolletta tramite conto corrente bancario o postale.\n" +
                "\n" +
                "\n" +
                "Aliquota IVA\n" +
                "\n" +
                "È la percentuale di IVA applicata agli importi presenti in bolletta.\n" +
                "\n" +
                "\n" +
                "ARERA\n" +
                "\n" +
                "È l’Autorità di Regolazione per Energia Reti e Ambiente.\n" +
                "\n" +
                "\n" +
                "Attivazione fornitura\n" +
                "\n" +
                "È l’avvio di una nuova fornitura di luce o gas oppure la riapertura di un punto già esistente.\n" +
                "\n" +
                "\n" +
                "Autolettura\n" +
                "\n" +
                "È la lettura del contatore comunicata direttamente dal cliente al fornitore.\n" +
                "\n" +
                "\n" +
                "Bolletta sintetica\n" +
                "\n" +
                "È il documento principale inviato al cliente con gli importi da pagare, i consumi e le scadenze.\n" +
                "\n" +
                "\n" +
                "Bonus sociale\n" +
                "\n" +
                "È un’agevolazione economica che riduce il costo delle bollette di luce o gas.\n" +
                "\n" +
                "\n" +
                "Cessazione amministrativa\n" +
                "\n" +
                "È la chiusura della fornitura per motivi contrattuali o mancati pagamenti.\n" +
                "\n" +
                "\n" +
                "Cliente domestico\n" +
                "\n" +
                "È una persona che utilizza luce o gas per la propria abitazione.\n" +
                "\n" +
                "\n" +
                "Codice cliente\n" +
                "\n" +
                "È un numero identificativo assegnato dal fornitore al contratto.\n" +
                "\n" +
                "\n" +
                "Conguaglio\n" +
                "\n" +
                "È un ricalcolo che allinea quanto pagato con i consumi reali.\n" +
                "\n" +
                "\n" +
                "Consumi effettivi\n" +
                "\n" +
                "Sono i consumi realmente registrati dal contatore.\n" +
                "\n" +
                "\n" +
                "Consumi stimati\n" +
                "\n" +
                "Sono consumi calcolati quando manca una lettura reale del contatore.\n" +
                "\n" +
                "\n" +
                "Corrispettivo\n" +
                "\n" +
                "È un importo dovuto per un servizio o una prestazione.\n" +
                "\n" +
                "\n" +
                "Data di emissione\n" +
                "\n" +
                "È il giorno in cui la bolletta viene generata dal fornitore.\n" +
                "\n" +
                "\n" +
                "Data di scadenza\n" +
                "\n" +
                "È il termine entro cui la bolletta deve essere pagata.\n" +
                "\n" +
                "\n" +
                "Deposito cauzionale\n" +
                "\n" +
                "È una somma richiesta come garanzia del pagamento delle bollette.\n" +
                "\n" +
                "\n" +
                "Dilazione di pagamento\n" +
                "\n" +
                "È la possibilità di pagare una bolletta a rate.\n" +
                "\n" +
                "\n" +
                "Dispacciamento\n" +
                "\n" +
                "È il servizio che mantiene in equilibrio il sistema elettrico tra energia prodotta e consumata.\n" +
                "\n" +
                "\n" +
                "Distributore\n" +
                "\n" +
                "È la società che gestisce reti, contatori e interventi tecnici.\n" +
                "\n" +
                "\n" +
                "Energia elettrica\n" +
                "\n" +
                "È l’energia utilizzata per alimentare dispositivi elettrici.\n" +
                "\n" +
                "\n" +
                "Esercente la vendita\n" +
                "\n" +
                "È il soggetto che vende energia elettrica o gas al cliente finale.\n" +
                "\n" +
                "\n" +
                "Fasce orarie\n" +
                "\n" +
                "Sono periodi della giornata in cui il prezzo dell’energia può cambiare.\n" +
                "\n" +
                "\n" +
                "Fornitore\n" +
                "\n" +
                "È l’azienda con cui il cliente firma il contratto e che invia la bolletta.\n" +
                "\n" +
                "\n" +
                "Gestione contatore\n" +
                "\n" +
                "Comprende installazione, manutenzione e rilevazione dei consumi del contatore.\n" +
                "\n" +
                "\n" +
                "Interessi di mora\n" +
                "\n" +
                "Sono importi aggiuntivi applicabili in caso di ritardo nel pagamento.\n" +
                "\n" +
                "\n" +
                "IVA\n" +
                "\n" +
                "È l’imposta sul valore aggiunto applicata alle bollette.\n" +
                "\n" +
                "\n" +
                "kWh (chilowattora)\n" +
                "\n" +
                "È l’unità di misura dei consumi di energia elettrica.\n" +
                "\n" +
                "\n" +
                "Lettura rilevata\n" +
                "\n" +
                "È la lettura del contatore effettuata dal distributore o trasmessa automaticamente.\n" +
                "\n" +
                "\n" +
                "Lettura stimata\n" +
                "\n" +
                "È una lettura calcolata in assenza di dati reali.\n" +
                "\n" +
                "\n" +
                "Mercato libero\n" +
                "\n" +
                "È il mercato in cui il cliente può scegliere liberamente il fornitore.\n" +
                "\n" +
                "\n" +
                "Misuratore elettronico\n" +
                "\n" +
                "È un contatore moderno che invia letture automatiche.\n" +
                "\n" +
                "\n" +
                "Morosità\n" +
                "\n" +
                "È la situazione in cui una o più bollette non vengono pagate entro la scadenza.\n" +
                "\n" +
                "\n" +
                "Offerta commerciale\n" +
                "\n" +
                "È la proposta economica del fornitore.\n" +
                "\n" +
                "\n" +
                "Oneri generali di sistema\n" +
                "\n" +
                "Sono costi destinati a finanziare attività di interesse generale del sistema energetico.\n" +
                "\n" +
                "\n" +
                "PDR\n" +
                "\n" +
                "È il codice che identifica il punto di riconsegna del gas naturale.\n" +
                "\n" +
                "\n" +
                "Perdite di rete\n" +
                "\n" +
                "Sono dispersioni tecniche di energia durante il trasporto sulla rete.\n" +
                "\n" +
                "\n" +
                "POD\n" +
                "\n" +
                "È il codice che identifica il punto di consegna dell’energia elettrica.\n" +
                "\n" +
                "\n" +
                "Potenza disponibile\n" +
                "\n" +
                "È la potenza che può essere utilizzata effettivamente.\n" +
                "\n" +
                "\n" +
                "Potenza impegnata\n" +
                "\n" +
                "È il livello di potenza previsto dal contratto.\n" +
                "\n" +
                "\n" +
                "Prezzo fisso\n" +
                "\n" +
                "È un’offerta in cui il prezzo dell’energia rimane invariato per un certo periodo.\n" +
                "\n" +
                "\n" +
                "Prezzo variabile\n" +
                "\n" +
                "È un’offerta in cui il prezzo cambia seguendo il mercato.\n" +
                "\n" +
                "\n" +
                "PSV\n" +
                "\n" +
                "Punto di Scambio Virtuale: riferimento italiano del prezzo all’ingrosso del gas.\n" +
                "\n" +
                "\n" +
                "PUN\n" +
                "\n" +
                "Prezzo Unico Nazionale: riferimento del mercato elettrico italiano usato nelle offerte a prezzo variabile.\n" +
                "\n" +
                "\n" +
                "Quota energia\n" +
                "\n" +
                "È la parte di costo collegata direttamente all’energia consumata.\n" +
                "\n" +
                "\n" +
                "Quota fissa\n" +
                "\n" +
                "È una parte della bolletta che si paga indipendentemente dai consumi.\n" +
                "\n" +
                "\n" +
                "Recesso\n" +
                "\n" +
                "È la richiesta di chiusura del contratto o cambio fornitore.\n" +
                "\n" +
                "\n" +
                "Riattivazione\n" +
                "\n" +
                "È il ripristino di una fornitura sospesa o cessata.\n" +
                "\n" +
                "\n" +
                "Ricalcolo\n" +
                "\n" +
                "È la correzione di importi già fatturati.\n" +
                "\n" +
                "\n" +
                "Rimborso\n" +
                "\n" +
                "È la restituzione di somme pagate in eccesso.\n" +
                "\n" +
                "\n" +
                "Servizi di rete\n" +
                "\n" +
                "Sono i costi legati al trasporto dell’energia e alla gestione del contatore.\n" +
                "\n" +
                "\n" +
                "Smc\n" +
                "\n" +
                "È l’unità di misura del gas: Standard metro cubo.\n" +
                "\n" +
                "\n" +
                "Sospensione della fornitura\n" +
                "\n" +
                "È l’interruzione del servizio per motivi tecnici o amministrativi.\n" +
                "\n" +
                "\n" +
                "Spesa per la materia energia\n" +
                "\n" +
                "È la parte della bolletta relativa all’acquisto e vendita dell’energia.\n" +
                "\n" +
                "\n" +
                "Subentro\n" +
                "\n" +
                "È l’attivazione di una fornitura su un contatore disattivato.\n" +
                "\n" +
                "\n" +
                "Tariffa\n" +
                "\n" +
                "È l’insieme dei prezzi e delle condizioni economiche della fornitura.\n" +
                "\n" +
                "\n" +
                "Tensione di alimentazione\n" +
                "\n" +
                "È il livello di tensione con cui viene fornita l’energia elettrica.\n" +
                "\n" +
                "\n" +
                "Utenza attiva\n" +
                "\n" +
                "È una fornitura regolarmente funzionante.\n" +
                "\n" +
                "\n" +
                "Utenza domestica non residente\n" +
                "\n" +
                "È la fornitura relativa a una seconda casa o casa senza residenza.\n" +
                "\n" +
                "\n" +
                "Utenza domestica residente\n" +
                "\n" +
                "È la fornitura intestata a chi ha la residenza nell’abitazione.\n" +
                "\n" +
                "\n" +
                "Voltura\n" +
                "\n" +
                "È il cambio intestatario di una fornitura attiva.\n" +
                "\n" +
                "\n" +
                "Voltura con cambio fornitore\n" +
                "\n" +
                "È il cambio intestatario insieme alla scelta di un nuovo venditore.";
        return val;
    }
}