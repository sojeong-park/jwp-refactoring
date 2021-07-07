package kitchenpos.menu.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Menu {
    private Long id;
    private String name;
    private BigDecimal price;
    /**
     * https://jdm.kr/blog/142 
     * 메뉴 객체는 한개의 메뉴 그룹에 속할 수 있다 : @ManyToOne
     * 메뉴 그룹 객체는 여러개의 메뉴를 가질 수 있다 : @OneToMany
     */
    @ManyToOne
    private Long menuGroupId;

    /**
     * 메뉴 객체는 여러개의 메뉴 상품을 가질 수 있다 : @OneToMany
     * 메뉴상품 객체는 한개의 메뉴에 속한다 : @ManyToOne
     */
    @OneToMany
    private List<MenuProduct> menuProducts;

    public Menu() {}

    public Menu(Long id, String name, BigDecimal price, Long menuGroupId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void updateName(final String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void updatePrice(final BigDecimal price) {
        this.price = price;
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public void updateMenuGroupId(final Long menuGroupId) {
        this.menuGroupId = menuGroupId;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public void updateMenuProducts(final List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public void addMenuProducts(MenuProduct menuProduct) {
        this.addMenuProducts(menuProduct);
    }
}
