package cl.rticket.controller;

import java.util.ArrayList;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cl.rticket.model.Partido;
import cl.rticket.model.Usuario;
import cl.rticket.model.modelReporte.AccesoPorPartido;
import cl.rticket.model.modelReporte.Partidos;
import cl.rticket.model.modelReporte.TicketEntradasPorMesSector;
import cl.rticket.model.modelReporte.TicketListaSector;
import cl.rticket.model.modelReporte.TicketPartioPorSector;
import cl.rticket.model.modelReporte.TicketPorDia;
import cl.rticket.model.modelReporte.TicketPorPartido;
import cl.rticket.services.ReporteService;

@Controller
public class ReportesController {
	@Autowired
	ReporteService reporteService ;
	
	@RequestMapping(value="/carga-pagina-reportes", method=RequestMethod.GET)
	public String cargaPaginaPartidos(Model model) {
		String devolver = "";
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		model.addAttribute("partido", new Partido());
		ArrayList<Partidos> list = reporteService.obtenerListaDePartidos(user.getIdEquipo());
		model.addAttribute("listaDeSectores", reporteService.obtenerListaTicketSector(user.getIdEquipo()));
		model.addAttribute("ListaPartidos", reporteService.obtenerListaDePartidos(user.getIdEquipo())) ;
		
		for ( int index = 0 ; index < list.size() ; index ++ ) {
			devolver += list.get(index).getPartido()+",";
		}
		model.addAttribute("listaPartidosFor", devolver);
		model.addAttribute("prueba", 1000);
		return "content/reportes";
	}
	@RequestMapping(value="/carga-pagina-reportes-acceso", method=RequestMethod.GET)
	public String cargaPaginaPartidosAccesos(Model model) {
		String devolverListaPartidos = "";
		String devolver = "";
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		ArrayList<AccesoPorPartido> list = reporteService.obtenerListaAccesoPorPartido(user.getIdEquipo());
		for ( int index = 0 ; index < list.size() ; index ++ ) {
			devolver += list.get(index).getCantidad()+",";
			devolverListaPartidos += list.get(index).getRival();
		}
		model.addAttribute("listaDeCantidad",devolver);
		model.addAttribute("listaPartidosAcceso",devolverListaPartidos);
		
		return "content/reportesAcceso";
	}
	@ModelAttribute("arrTicketSectorMesAnioSector")
	public String  listaTicketPorSectorMesAnio( ) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		ArrayList<TicketEntradasPorMesSector> a = reporteService.obtenerListaTicketEntradasPorMesSector(user.getIdEquipo());
		String devuelta ="" ;
		for ( int index = 0 ; index < a.size(); index ++ ) {
			
				devuelta += a.get(index).getSector()+",";
		}
		return devuelta;
	}
	@ModelAttribute("arrTicketDineroMesAnioSector")
	public String  listaTicketPorDineroMesAnio( ) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		ArrayList<TicketEntradasPorMesSector> a = reporteService.obtenerListaTicketEntradasPorMesSector(user.getIdEquipo());
		String devuelta ="" ;
		for ( int index = 0 ; index < a.size(); index ++ ) {
			
				devuelta += a.get(index).getMonto()+",";
		}
		return devuelta;
	}
	@ModelAttribute("arrTicketCantidadMesAnioSector")
	public String  listaTicketPorCantidadMesAnio( ) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		ArrayList<TicketEntradasPorMesSector> a = reporteService.obtenerListaTicketEntradasPorMesSector(user.getIdEquipo());
	
		String devuelta ="" ;
		for ( int index = 0 ; index < a.size(); index ++ ) {
			
				devuelta += a.get(index).getCantidadEntradas()+",";
		}
		return devuelta;
	}
	@ModelAttribute("arrTicketMesAnioSector")
	public String  listaTicketPorMesAnio( ) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		ArrayList<TicketEntradasPorMesSector> a = reporteService.obtenerListaTicketEntradasPorMesSector(user.getIdEquipo());
		String [] arrMes = { "ene","feb","mar","abr","may","jun","jul","ago","sep","oct","nov","dic"};
		String devuelta ="" ;
		for ( int index = 0 ; index < a.size(); index ++ ) {
				int val = a.get(index).getMes();
				devuelta += a.get(index).getAnio()+"-"+arrMes[val-1].toUpperCase()+",";
		}
		System.out.println("Aqui el mes año "+devuelta);
		return devuelta;
	}
	
	@ModelAttribute("arrTicketCantidadDinerPorDia")
	public String  listaTicketPorDiaDinero( ) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		ArrayList<TicketPorDia> a = reporteService.obtenerListaTicketPorDia(user.getIdEquipo());
		
		String devuelta ="" ;
		for ( int index = 0 ; index < a.size(); index ++ ) {
				devuelta += a.get(index).getMonto()+",";
		}
	
		return devuelta;
	}
	@ModelAttribute("arrTicketPorDiaDias")
	public String  listaTicketPorDiaDias ( ) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		ArrayList<TicketPorDia> a = reporteService.obtenerListaTicketPorDia(user.getIdEquipo());
		String devuelta ="" ;
		String [] arrMes = { "ene","feb","mar","abr","may","jun","jul","ago","sep","oct","nov","dic"};
		for ( int index = 0 ; index < a.size(); index ++ ) {
		
				int var = Integer.parseInt(a.get(index).getFechaCompra().substring(3, 5));
				devuelta += a.get(index).getFechaCompra().substring(0, 2)+"-"+arrMes[var-1]+",";
		}
	
		return devuelta;
	}
	@ModelAttribute("arrTicketPorDiaCantidad")
	public String  listaTicketPorDiaCantidad ( ) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		ArrayList<TicketPorDia> a = reporteService.obtenerListaTicketPorDia(user.getIdEquipo());
		String devuelta ="" ;
		for ( int index = 0 ; index < a.size(); index ++ ) {
				devuelta += a.get(index).getCantidadEntrada()+",";
		}
	
		return devuelta;
	}
	@ModelAttribute("arrTicketPorDiaPartidos")
	public String  listaTicketPorDiaPartidos ( ) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		ArrayList<TicketPorDia> a = reporteService.obtenerListaTicketPorDia(user.getIdEquipo());
		String devuelta ="" ;
		for ( int index = 0 ; index < a.size(); index ++ ) {
				devuelta += a.get(index).getIdPartido()+",";
		}
	
		return devuelta;
	}
	@ModelAttribute("arrPartidosPorSectorMonto")
	public String  listaPartidosPorSectorMonto ( ) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		ArrayList<TicketPartioPorSector> a = reporteService.obtenerListaTicketPartidoPorSector(user.getIdEquipo());
		String devuelta ="" ;
		for ( int index = 0 ; index < a.size(); index ++ ) {
				devuelta += a.get(index).getMonto()+",";
		}

		return devuelta;
	}
	@ModelAttribute("arrPartidosPorSector")
	public String  listaPartidosPorSector ( ) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		ArrayList<TicketPartioPorSector> a = reporteService.obtenerListaTicketPartidoPorSector(user.getIdEquipo());
		String devuelta ="" ;
		for ( int index = 0 ; index < a.size(); index ++ ) {
				devuelta += a.get(index).getRival()+",";
		}
		return devuelta;
	}
	@ModelAttribute("arrCantidadDeEntradas")
	public String  listaCantidadEntradas ( ) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		ArrayList<TicketPartioPorSector> a = reporteService.obtenerListaTicketPartidoPorSector(user.getIdEquipo());
		String devuelta ="" ;
		for ( int index = 0 ; index < a.size(); index ++ ) {
				devuelta += a.get(index).getCantidadEntradas()+",";
		}
		return devuelta;
	}
	@ModelAttribute("arrPartidosPorSectorMuestraSector")
	public String  listaPartidosPorSectorMuestra ( ) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		ArrayList<TicketPartioPorSector> a = reporteService.obtenerListaTicketPartidoPorSector(user.getIdEquipo());
		String devuelta ="" ;
		for ( int index = 0 ; index < a.size(); index ++ ) {
				devuelta += a.get(index).getSector()+",";
		}
		return devuelta;
	}
	@ModelAttribute("arrStringPartido")
	public String  datoSectorPartidoEntrada ( ) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		ArrayList<Partidos> a = reporteService.obtenerListaDePartidos(user.getIdEquipo());
		String devuelta ="" ;
		for ( int index = 0 ; index < a.size(); index ++ ) {
				devuelta += a.get(index).getPartido()+",";
		}
		return devuelta;
	}
	@ModelAttribute("arrListaSectorString")
	public String  datoSectorString ( ) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		ArrayList<TicketListaSector> a = reporteService.obtenerListaTicketSector(user.getIdEquipo());
		String devuelta ="" ;
		for ( int index = 0 ; index < a.size(); index ++ ) {
				devuelta += a.get(index).getNombreSector()+",";
		}
		return devuelta;
	}

	@ModelAttribute("arrString")
	public String  datoString ( ) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		ArrayList<TicketPorPartido> a = reporteService.obtenerListaTicketPartido(user.getIdEquipo());
		String devuelta ="" ;
		for ( int index = 0 ; index < a.size(); index ++ ) {
				devuelta += a.get(index).getRival()+",";
		}
		return devuelta;
	}
	@ModelAttribute("arrNum")
	public String  datoNumeico ( ) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		ArrayList<TicketPorPartido> a = reporteService.obtenerListaTicketPartido(user.getIdEquipo());
		String devuelta ="" ;
		for ( int index = 0 ; index < a.size(); index ++ ) {
				devuelta += a.get(index).getCantidadEntradas()+",";
		}
		return devuelta;
	}
	@ModelAttribute("arrDinero")
	public String  datoDinero ( ) {
		Usuario user = (Usuario)SecurityUtils.getSubject().getSession().getAttribute("usuario");
		ArrayList<TicketPorPartido> a = reporteService.obtenerListaTicketPartido(user.getIdEquipo());
		String devuelta ="" ;
		for ( int index = 0 ; index < a.size(); index ++ ) {
				devuelta += a.get(index).getMonto()+",";
		}
		return devuelta;
	}
	
	/*Solo*/
}
