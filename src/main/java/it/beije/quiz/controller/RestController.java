package it.beije.quiz.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import it.beije.quiz.Utils;
import it.beije.quiz.model.Domanda;


@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	static String baseDirectory = "C:\\Users\\Padawan04\\git\\Quiz\\domande\\";
	
	@RequestMapping(value = "/domande", method = RequestMethod.GET)
	public @ResponseBody List<Domanda> getDomande() throws ParserConfigurationException, SAXException, IOException {
		List<Domanda> domande = new ArrayList<Domanda>();
		File f = new File("C:\\Users\\Padawan04\\git\\Quiz\\domande\\");
		String dir = "";
		for(File file : f.listFiles()) {
			if(file.isDirectory()) {
				dir = file.getName();
				for(File ff : file.listFiles()) {
					domande.addAll(Utils.readFileDomande(ff.getAbsolutePath(), dir));
				}
			} 
		}
		return domande;
	}
	
	@RequestMapping(value = "/domande/{libro}", method = RequestMethod.GET)
	public @ResponseBody List<Domanda> getDomandeLibro(@PathVariable String libro) throws ParserConfigurationException, SAXException, IOException {
		List<Domanda> domande = new ArrayList<Domanda>();
		File f = new File(baseDirectory + libro);
		for(File file : f.listFiles()) {
			if(!file.isDirectory()) {
				domande.addAll(Utils.readFileDomande(file.getAbsolutePath(), libro));
			} 
		}
		return domande;
	}
	
	@RequestMapping(value = "/domande/{libro}/{cap}", method = RequestMethod.GET)
	public @ResponseBody List<Domanda> getDomandeLibro(@PathVariable String libro, @PathVariable String cap) throws ParserConfigurationException, SAXException, IOException {
		List<Domanda> domande = getDomandeLibro(libro);		
		List<Domanda> returnList = new ArrayList<Domanda>();
		
		for(Domanda d: domande) {
			if(d.getChapter().equals(cap)) {
				returnList.add(d);
			}
		}
		return returnList;
	}
	
	@RequestMapping(value = "/newdomanda", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Domanda newDomanda(@RequestBody Domanda domanda) throws Exception {
		Utils.writeDomandeXML(domanda, baseDirectory + getChapter(domanda.getId()));
		return domanda;
	}
	
	
	@RequestMapping(value = "/newdomande", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody void newDomande(@RequestBody List<Domanda> domande) throws Exception {
		for(Domanda d : domande) {
			Utils.writeDomandeXML(d, baseDirectory + getChapter(d.getId()));
		}
	}
	
	
	private String getChapter(String id) {
		String[] array = id.split("([|])");
		return array[0] + "\\domande_cap" + array[1] + ".xml";
	}
	
}
