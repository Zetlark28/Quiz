package it.beije.quiz.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.beije.quiz.Utils;
import it.beije.quiz.model.Domanda;
import it.beije.quiz.model.Libro;
import it.beije.quiz.service.QuizService;

@RestController
public class ControllerRest {


	@Autowired
	private QuizService quizService;

	private List<File> listaFile = null;
	
//	
//	@RequestMapping(value="/caricaDomanda/{dirLibro}/{capitolo}/{nDomanda}", method=RequestMethod.GET)
//	public @ResponseBody Domanda getDomanda(@PathVariable String dirLibro, @PathVariable int capitolo, @PathVariable int nDomanda ){
//		List <Domanda> listaDomande= new ArrayList<Domanda>();
//		List<File> listaFile= Utils.selezionaFileDiInteresse(dirLibro);
//
//		for(File f: listaFile) {
//
//
//			listaDomande.addAll(Utils.readFileDomande(f.getPath()));
//		}
//
//		String idDomanda = dirLibro + "|" + capitolo + "|" + nDomanda;
//		for (Domanda d : listaDomande)
//			if (d.getId().equals(idDomanda))
//				return d;
//
//		return null;
//
//
//	}
	

	@RequestMapping(value = "/domande/{dirLibro}/{capitolo}", method = RequestMethod.GET)
	public @ResponseBody List<Domanda> getDomande(@PathVariable String dirLibro, @PathVariable int capitolo,@RequestParam(name = "nDomanda", required = false) Integer nDomanda) {
		List<Domanda> listaDomande = quizService.getDomande();
		List<Domanda> domande= new ArrayList<Domanda>();

		if(nDomanda==null) {
			for(Domanda d:listaDomande)
				if(d.getChapter()==capitolo && Utils.getDirectory(d.getId()).equals(dirLibro))
					domande.add(d);
		}
		else {
			String idDomanda = dirLibro + "|" + capitolo + "|" + nDomanda;

			for(Domanda d : listaDomande)
				if(d.getId().equals(idDomanda)) {
					domande.add(d);
					break;
				}
			
		}
		
		return domande;
		

	}

	@RequestMapping(value = "/aggionadomanda", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Domanda aggiornaDomanda(@RequestBody Domanda dom) {
		StringBuilder path = new StringBuilder();
		path.append(quizService.getBaseDirectory())
				.append(Utils.getDirectory(dom.getId()) + "\\domande_cap" + Utils.getCapitolo(dom.getId()) + ".xml");
		List<Domanda> lettura = Utils.readFileDomande(path.toString());
		for (Domanda doma : lettura) {
			if (doma.getId().equals(dom.getId())) {
				Utils.eliminaDomanda(doma, new File(path.toString()));
				Utils.aggiungiDomanda(dom, new File(path.toString()));
				return dom;
			}
			
		}
		return null;

		

	}

	@RequestMapping(value = "/insertListaDomande", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody void insertDomande(@RequestBody List<Domanda> domande) {
		if(domande.size()==0) {
			System.out.println("INSERIRE ALMENO UNA DOMANDA!");
		}
		else if(domande.size()==1) {
			quizService.insertDomanda(domande.get(0));
		}
		else {
			for (Domanda dom : domande) 
				quizService.insertDomanda(dom);
			}
	}

	@RequestMapping(value = "/updateListaDomande", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Domanda> updateListaDomande(@RequestBody List <Domanda> domande, HttpServletResponse response) {
		List <Domanda> listaDomandeAggiornate = new ArrayList<Domanda>();
		Domanda d=null;
		for(Domanda domanda: domande) {
			d=aggiornaDomanda(domanda);
			if (d!=null)
				listaDomandeAggiornate.add(domanda);
			else {
				response.setStatus(204);
				break;
			}

		}

		if(listaDomandeAggiornate==null) 
			response.setStatus(204);

			return listaDomandeAggiornate;		
		


	}

	@RequestMapping(value = "/deleteDomanda", method = RequestMethod.DELETE)
	public @ResponseBody boolean  deleteDomanda( HttpServletResponse response, @RequestParam(name="idDomanda",required = true) String idDomanda) {
		
		String dirLibro=Utils.getDirectory(idDomanda);
		int capitolo=Utils.getCapitolo(idDomanda);
		int nDomanda=Utils.getNDomanda(idDomanda);
		Domanda d= getDomande(dirLibro,capitolo,nDomanda).get(0);
		if(d==null) {
			response.setStatus(204);
			return false;
		}
		else {
			StringBuilder path = new StringBuilder(quizService.getBaseDirectory()+dirLibro+"\\domande_cap"+ capitolo +".xml");
	        String pathdomanda= path.toString();
			File file2=new File(pathdomanda);
			Utils.eliminaDomanda(d,file2);
			return true;
			
		}
	
	}

	
//	@RequestMapping(value = "/deleteDomande", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody void deleteDomande(@RequestBody List <Domanda> domande, HttpServletResponse response){
//		
//		for(Domanda d: domande) {
//			int nDomanda= d.getQuestion();
//			String dirLibro=Utils.getDirectory(d.getId());
//			int capitolo=d.getChapter();
//			deleteDomanda(dirLibro,capitolo,nDomanda,response);
//			}
//		}
	
}


	
	
	

