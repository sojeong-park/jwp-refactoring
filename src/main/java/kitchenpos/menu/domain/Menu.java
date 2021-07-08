package kitchenpos.menu.domain;

import kitchenpos.common.Name;
import kitchenpos.common.Price;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Name name;
    private Price price;

    @ManyToOne
    @JoinColumn(name = "menu_group_id")
    private MenuGroup menuGroup;

    @Embedded
    private MenuProducts menuProducts;

    public Menu() {}

    public Menu(Long id, Name name, Price price, MenuGroup menuGroup) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
    }

    public Menu(String name, BigDecimal price, MenuGroup menuGroup, List<MenuProduct> menuProducts) {
        this.name = new Name(name);
        this.price = new Price(price);
        this.menuGroup = menuGroup;
        this.menuProducts = new MenuProducts(menuProducts, this.price.getPrice());
    }

    public Menu(Long id, Name name, Price price, MenuGroup menuGroup, List<MenuProduct> menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.menuProducts = new MenuProducts(menuProducts, this.price.getPrice());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public void updateName(final String name) {
        this.name.updateName(name);
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public void updatePrice(final BigDecimal price) {
        this.price.updatePrice(price);
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public void updateMenuGroup(final MenuGroup menuGroup) {
        this.menuGroup = menuGroup;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts.getMenuProducts();
    }

//    public void updateMenuProducts(final List<MenuProduct> menuProducts) {
//        this.menuProducts.addMenuProducts(menuProducts);;
//    }

    public void addMenuProducts(MenuProduct menuProduct) {
        this.menuProducts.addMenuProducts(menuProduct);
    }
}
