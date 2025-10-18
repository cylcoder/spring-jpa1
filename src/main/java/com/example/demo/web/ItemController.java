package com.example.demo.web;

import com.example.demo.domain.Book;
import com.example.demo.domain.Item;
import com.example.demo.service.ItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;

  @GetMapping("/items/new")
  public String createForm(Model model) {
    model.addAttribute("form", new BookForm());
    return "items/createItemForm";
  }

  @PostMapping("/items/new")
  public String create(BookForm form) {
    Book book = new Book();
    book.setName(form.getName());
    book.setPrice(form.getPrice());
    book.setAuthor(form.getAuthor());
    book.setStockQuantity(form.getStockQuantity());
    book.setIsbn(form.getIsbn());
    itemService.save(book);
    return "redirect:/items";
  }

  @GetMapping("/items")
  public String list(Model model) {
    List<Item> items = itemService.findAll();
    model.addAttribute("items", items);
    return "items/items";
  }

  @GetMapping("/items/{itemId}/edit")
  public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
    Book book = (Book) itemService.findById(itemId);
    BookForm form = new BookForm();
    form.setId(book.getId());
    form.setName(book.getName());
    form.setPrice(book.getPrice());
    form.setStockQuantity(book.getStockQuantity());
    form.setAuthor(book.getAuthor());
    form.setIsbn(book.getIsbn());

    model.addAttribute("form", form);
    return "items/updateItemForm";
  }

  @PostMapping("/items/{itemId}/edit")
  public String updateItem(@ModelAttribute("form") BookForm form) {
    itemService.update(
        form.getId(),
        form.getName(),
        form.getAuthor(),
        form.getIsbn(),
        form.getPrice(),
        form.getStockQuantity()
    );
    return "redirect:/items";
  }

}
