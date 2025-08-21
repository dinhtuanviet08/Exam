package com.example.playereval.web;

import com.example.playereval.entity.*;
import com.example.playereval.repo.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.transaction.Transactional;

import java.util.List;

@Controller
@RequestMapping("/players")
public class PlayerController {

    private final PlayerRepository playerRepository;
    private final IndexerRepository indexerRepository;
    private final PlayerIndexRepository playerIndexRepository;

    public PlayerController(PlayerRepository playerRepository, IndexerRepository indexerRepository, PlayerIndexRepository playerIndexRepository) {
        this.playerRepository = playerRepository;
        this.indexerRepository = indexerRepository;
        this.playerIndexRepository = playerIndexRepository;
    }

    // Hiển thị danh sách player
    @GetMapping
    public String list(Model model) {
        model.addAttribute("players", playerRepository.findAll());
        return "players/list";
    }

    // Form tạo mới player
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("playerForm", new PlayerForm());
        model.addAttribute("indexers", indexerRepository.findAll());
        return "players/form";
    }

    // Xử lý tạo player
    @PostMapping
    public String create(@Valid @ModelAttribute("playerForm") PlayerForm form,
                         BindingResult result,
                         Model model,
                         RedirectAttributes ra) {

        Indexer idx = null;
        if (form.getIndexerId() != null) {
            idx = indexerRepository.findById(form.getIndexerId()).orElse(null);
        }

        if (idx != null) {
            if (form.getValue() == null || form.getValue() < idx.getValueMin() || form.getValue() > idx.getValueMax()) {
                result.rejectValue("value", "Value.range", new Object[]{idx.getValueMin(), idx.getValueMax()}, null);
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("indexers", indexerRepository.findAll());
            return "players/form";
        }

        Player p = new Player();
        p.setName(form.getName());
        p.setAge(form.getAge());
        p.setIndexer(idx);
        p = playerRepository.save(p);

        PlayerIndex pi = new PlayerIndex();
        pi.setPlayer(p);
        pi.setIndexer(idx);
        pi.setValue(form.getValue().intValue());
        playerIndexRepository.save(pi);

        ra.addFlashAttribute("msg", "Player created");
        return "redirect:/players";
    }

    // Form edit player
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        Player p = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        PlayerForm form = new PlayerForm();
        form.setPlayerId(p.getPlayerId());
        form.setName(p.getName());
        form.setAge(p.getAge());
        form.setIndexerId(p.getIndexer().getIndexId());

        List<PlayerIndex> list = playerIndexRepository.findByPlayer_PlayerId(id);
        if (!list.isEmpty()) {
            form.setValue(list.get(0).getValue().floatValue());
        }

        model.addAttribute("playerForm", form);
        model.addAttribute("indexers", indexerRepository.findAll());
        return "players/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid @ModelAttribute("playerForm") PlayerForm form,
                         BindingResult result,
                         Model model,
                         RedirectAttributes ra) {

        Player p = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        Indexer idx = indexerRepository.findById(form.getIndexerId()).orElse(null);

        if (idx != null) {
            if (form.getValue() == null || form.getValue() < idx.getValueMin() || form.getValue() > idx.getValueMax()) {
                result.rejectValue("value", "Value.range", new Object[]{idx.getValueMin(), idx.getValueMax()}, null);
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("indexers", indexerRepository.findAll());
            return "players/form";
        }

        p.setName(form.getName());
        p.setAge(form.getAge());
        p.setIndexer(idx);
        playerRepository.save(p);

        List<PlayerIndex> list = playerIndexRepository.findByPlayer_PlayerId(id);
        PlayerIndex pi = list.isEmpty() ? new PlayerIndex() : list.get(0);
        pi.setPlayer(p);
        pi.setIndexer(idx);
        pi.setValue(form.getValue().intValue());
        playerIndexRepository.save(pi);

        ra.addFlashAttribute("msg", "Player updated");
        return "redirect:/players";
    }

    @PostMapping("/{id}/delete")
    @Transactional
    public String delete(@PathVariable("id") Integer id, RedirectAttributes ra) {
        playerIndexRepository.deleteByPlayer_PlayerId(id);
        playerRepository.deleteById(id);
        ra.addFlashAttribute("msg", "Player deleted");
        return "redirect:/players";
    }
}
