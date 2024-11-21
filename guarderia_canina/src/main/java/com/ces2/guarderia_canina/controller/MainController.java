package com.ces2.guarderia_canina.controller;

import com.ces2.guarderia_canina.enums.TipoServicio;
import com.ces2.guarderia_canina.model.Cuidador;
import com.ces2.guarderia_canina.model.Dueno;
import com.ces2.guarderia_canina.model.Perro;
import com.ces2.guarderia_canina.model.Servicio;
import com.ces2.guarderia_canina.repository.CuidadorRepository;
import com.ces2.guarderia_canina.repository.DuenoRepository;
import com.ces2.guarderia_canina.repository.PerroRepository;
import com.ces2.guarderia_canina.repository.ServicioRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/")
public class MainController {

    private final PerroRepository perroRepository;
    private final DuenoRepository duenoRepository;
    private final ServicioRepository servicioRepository;
    private final CuidadorRepository cuidadorRepository;

    public MainController(PerroRepository perroRepository, DuenoRepository duenoRepository,
                          ServicioRepository servicioRepository, CuidadorRepository cuidadorRepository) {
        this.perroRepository = perroRepository;
        this.duenoRepository = duenoRepository;
        this.servicioRepository = servicioRepository;
        this.cuidadorRepository = cuidadorRepository;
    }

    // ** CRUD para Perros **
    @GetMapping("/perros")
    public String listarPerros(Model model) {
        Iterable<Perro> perros = perroRepository.findAll();
        Map<Long, String> duenoMap = new HashMap<>();
        duenoRepository.findAll().forEach(dueno -> duenoMap.put(dueno.getId(), dueno.getNombre()));

        List<Map<String, Object>> perrosConDueños = new ArrayList<>();
        for (Perro perro : perros) {
            Map<String, Object> perroConDueno = new HashMap<>();
            perroConDueno.put("id", perro.getId());
            perroConDueno.put("nombre", perro.getNombre());
            perroConDueno.put("raza", perro.getRaza());
            perroConDueno.put("edad", perro.getEdad());
            perroConDueno.put("peso", perro.getPeso());
            perroConDueno.put("dueno", duenoMap.getOrDefault(perro.getDuenoId(), "Sin dueño"));
            perrosConDueños.add(perroConDueno);
        }
        model.addAttribute("perros", perrosConDueños);
        return "perros/lista";
    }

    @GetMapping("/perros/nuevo")
    public String nuevoPerro(Model model) {
        model.addAttribute("perro", new Perro());
        model.addAttribute("duenos", duenoRepository.findAll());
        return "perros/formulario";
    }

    @PostMapping("/perros")
    public String guardarPerro(@ModelAttribute Perro perro) {
        perroRepository.save(perro);
        return "redirect:/perros";
    }

    @GetMapping("/perros/editar/{id}")
    public String editarPerro(@PathVariable Long id, Model model) {
        Perro perro = perroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Perro no encontrado con ID: " + id));
        model.addAttribute("perro", perro);
        model.addAttribute("duenos", duenoRepository.findAll());
        return "perros/formularioEditar";
    }

    @PostMapping("/perros/editar/{id}")
    public String actualizarPerro(@PathVariable Long id, @ModelAttribute Perro perro) {
        perro.setId(id);
        perroRepository.save(perro);
        return "redirect:/perros";
    }

    @GetMapping("/perros/eliminar/{id}")
    public String eliminarPerro(@PathVariable Long id) {
        perroRepository.deleteById(id);
        return "redirect:/perros";
    }

    // ** CRUD para Dueños **
    @GetMapping("/duenos")
    public String listarDuenos(Model model) {
        model.addAttribute("duenos", duenoRepository.findAll());
        return "duenos/lista";
    }

    @GetMapping("/duenos/nuevo")
    public String nuevoDueno(Model model) {
        model.addAttribute("dueno", new Dueno());
        return "duenos/formulario";
    }

    @PostMapping("/duenos")
    public String guardarDueno(@ModelAttribute Dueno dueno) {
        duenoRepository.save(dueno);
        return "redirect:/duenos";
    }

    @GetMapping("/duenos/editar/{id}")
    public String editarDueno(@PathVariable Long id, Model model) {
        Dueno dueno = duenoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dueño no encontrado con ID: " + id));
        model.addAttribute("dueno", dueno);
        return "duenos/formularioEditar";
    }

    @PostMapping("/duenos/editar/{id}")
    public String actualizarDueno(@PathVariable Long id, @ModelAttribute Dueno dueno) {
        dueno.setId(id);
        duenoRepository.save(dueno);
        return "redirect:/duenos";
    }

    @GetMapping("/duenos/eliminar/{id}")
    public String eliminarDueno(@PathVariable Long id) {
        duenoRepository.deleteById(id);
        return "redirect:/duenos";
    }

    // ** CRUD para Servicios **
    @GetMapping("/servicios")
    public String listarServicios(Model model) {
        Iterable<Servicio> servicios = servicioRepository.findAll();
        Map<Long, String> perroMap = new HashMap<>();
        perroRepository.findAll().forEach(perro -> perroMap.put(perro.getId(), perro.getNombre()));

        List<Map<String, Object>> serviciosConPerro = new ArrayList<>();
        for (Servicio servicio : servicios) {
            Map<String, Object> servicioConPerro = new HashMap<>();
            servicioConPerro.put("id", servicio.getId());
            servicioConPerro.put("tipoServicio", servicio.getTipoServicio());
            servicioConPerro.put("costo", servicio.getCosto());
            servicioConPerro.put("duracion", servicio.getDuracion());
            servicioConPerro.put("perro", perroMap.getOrDefault(servicio.getPerroId(), "Sin perro asociado"));
            serviciosConPerro.add(servicioConPerro);
        }

        model.addAttribute("servicios", serviciosConPerro);
        return "servicios/lista";
    }


    @GetMapping("/servicios/nuevo")
    public String nuevoServicio(Model model) {
        model.addAttribute("servicio", new Servicio());
        model.addAttribute("perros", perroRepository.findAll());
        model.addAttribute("tiposServicio", TipoServicio.values());
        return "servicios/formulario";
    }

    @PostMapping("/servicios")
    public String guardarServicio(@ModelAttribute Servicio servicio) {
        servicioRepository.save(servicio);
        return "redirect:/servicios";
    }

    @GetMapping("/servicios/editar/{id}")
    public String editarServicio(@PathVariable Long id, Model model) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado con ID: " + id));
        model.addAttribute("servicio", servicio);
        model.addAttribute("perros", perroRepository.findAll());
        model.addAttribute("tiposServicio", TipoServicio.values());
        return "servicios/formularioEditar";
    }

    @PostMapping("/servicios/editar/{id}")
    public String actualizarServicio(@PathVariable Long id, @ModelAttribute Servicio servicio) {
        servicio.setId(id);
        servicioRepository.save(servicio);
        return "redirect:/servicios";
    }

    @GetMapping("/servicios/eliminar/{id}")
    public String eliminarServicio(@PathVariable Long id) {
        servicioRepository.deleteById(id);
        return "redirect:/servicios";
    }

    // ** CRUD para Cuidadores **
    @GetMapping("/cuidadores")
    public String listarCuidadores(Model model) {
        model.addAttribute("cuidadores", cuidadorRepository.findAll());
        return "cuidadores/lista";
    }

    @GetMapping("/cuidadores/nuevo")
    public String nuevoCuidador(Model model) {
        model.addAttribute("cuidador", new Cuidador());
        return "cuidadores/formulario";
    }

    @PostMapping("/cuidadores")
    public String guardarCuidador(@ModelAttribute Cuidador cuidador) {
        cuidadorRepository.save(cuidador);
        return "redirect:/cuidadores";
    }

    @GetMapping("/cuidadores/editar/{id}")
    public String editarCuidador(@PathVariable Long id, Model model) {
        Cuidador cuidador = cuidadorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cuidador no encontrado con ID: " + id));
        model.addAttribute("cuidador", cuidador);
        return "cuidadores/formularioEditar";
    }

    @PostMapping("/cuidadores/editar/{id}")
    public String actualizarCuidador(@PathVariable Long id, @ModelAttribute Cuidador cuidador) {
        cuidador.setId(id);
        cuidadorRepository.save(cuidador);
        return "redirect:/cuidadores";
    }

    @GetMapping("/cuidadores/eliminar/{id}")
    public String eliminarCuidador(@PathVariable Long id) {
        cuidadorRepository.deleteById(id);
        return "redirect:/cuidadores";
    }
}
