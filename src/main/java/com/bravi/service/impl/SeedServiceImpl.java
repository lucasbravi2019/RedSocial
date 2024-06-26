package com.bravi.service.impl;

import com.bravi.constant.PublicacionTypeEnum;
import com.bravi.entity.*;
import com.bravi.exception.InvalidPublicacionLengthException;
import com.bravi.exception.LikeNotAvailableException;
import com.bravi.service.CuentaService;
import com.bravi.service.SeedService;
import com.bravi.util.ListUtils;

import java.time.LocalDate;
import java.util.List;

public class SeedServiceImpl implements SeedService {

    private final CuentaService cuentaService;

    public SeedServiceImpl(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }


    @Override
    public void addCuentas() {
        cuentaService.guardarCuentas(getCuentas());

        addSeguidores();
    }

    private void likePublicacionNoPermitida(Publicacion<?> publicacion) {
        try {
            cuentaService.logUser("pop3");
            cuentaService.likePublicacion(publicacion);
        } catch (LikeNotAvailableException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void publicacionContenidoExtenso() {
        try {
            cuentaService.logUser("user5");
            cuentaService.publicarContenido(new ContenidoTexto(getContenidoExtenso()), PublicacionTypeEnum.AMBAS);
        } catch (InvalidPublicacionLengthException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void publicar() {
        cuentaService.logUser("empresa1");
        cuentaService.publicarContenido(new ContenidoTexto("Hola, soy Empresa1"), PublicacionTypeEnum.AMBAS);
        cuentaService.publicarContenido(new ContenidoTexto("Hola, acá de nuevo Empresa1"), PublicacionTypeEnum.LIKEABLE);

        cuentaService.logUser("empresa2");
        cuentaService.publicarContenido(new ContenidoTexto("Hola, soy Empresa2"), PublicacionTypeEnum.AMBAS);
        Publicacion<?> publicacion1 = cuentaService.publicarContenido(new ContenidoTexto("Hola, acá de nuevo Empresa2"),
                PublicacionTypeEnum.REPOSTEABLE);

        cuentaService.logUser("pop1");
        Publicacion<?> publicacion2 = cuentaService.publicarContenido(new ContenidoTexto("Hola, soy Pop1 amiga de @Pop2"),
                PublicacionTypeEnum.AMBAS);

        cuentaService.logUser("pop2");
        cuentaService.republicarContenido(publicacion2);

        cuentaService.logUser("user2");
        Publicacion<?> publicacion3 = cuentaService
                .publicarContenido(new ContenidoTexto("Que bueno estar con @User3 y @User4 viendo a Pop1"),
                        PublicacionTypeEnum.LIKEABLE);

        cuentaService.logUser("user1");
        cuentaService.likePublicacion(publicacion3);

        cuentaService.logUser("user3");
        cuentaService.likePublicacion(publicacion3);

        cuentaService.logUser("user4");
        cuentaService.likePublicacion(publicacion3);

        cuentaService.logUser("empresa1");
        cuentaService.mostrarAlcance();

        cuentaService.logUser("user3");
        cuentaService.likePublicacion(publicacion3);

        likePublicacionNoPermitida(publicacion1);

        publicacionContenidoExtenso();
    }

    private String getContenidoExtenso() {
        return "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam " +
                "rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt " +
                "explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia " +
                "consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, " +
                "qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi " +
                "tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, " +
                "quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea " +
                "commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil" +
                " molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?";
    }

    private void addSeguidores() {
        cuentaService.addSeguidor("empresa1", "pop1");
        cuentaService.addSeguidor("empresa1", "pop2");
        cuentaService.addSeguidor("empresa1", "user1");
        cuentaService.addSeguidor("empresa1", "user5");

        cuentaService.addSeguidor("empresa2", "pop1");
        cuentaService.addSeguidor("empresa2", "pop3");
        cuentaService.addSeguidor("empresa2", "user1");
        cuentaService.addSeguidor("empresa2", "user2");

        cuentaService.addSeguidor("pop1", "user2");
        cuentaService.addSeguidor("pop1", "user3");
        cuentaService.addSeguidor("pop1", "pop3");

        cuentaService.addSeguidor("pop2", "user4");
        cuentaService.addSeguidor("pop2", "user5");

        cuentaService.addSeguidor("pop3", "user1");
        cuentaService.addSeguidor("pop3", "user5");

        cuentaService.addSeguidor("user2", "user1");
        cuentaService.addSeguidor("user3", "user4");
        cuentaService.addSeguidor("user4", "user3");
        cuentaService.addSeguidor("user5", "user4");
    }

    private List<Cuenta> getCuentas() {
        Cuenta user1 = CuentaNormal.builder()
                .nombre("User 1")
                .email("user1@mail.com")
                .username("user1")
                .fechaNacimiento(LocalDate.of(2001, 1, 3))
                .build();

        Cuenta user2 = CuentaNormal.builder()
                .nombre("User 2")
                .email("user2@mail.com")
                .username("user2")
                .fechaNacimiento(LocalDate.of(2001, 1, 3))
                .build();

        Cuenta user3 = CuentaNormal.builder()
                .nombre("User 3")
                .email("user3@mail.com")
                .username("user3")
                .fechaNacimiento(LocalDate.of(2001, 1, 3))
                .build();
        Cuenta user4 = CuentaNormal.builder()
                .nombre("User 4")
                .email("user4@mail.com")
                .username("user4")
                .fechaNacimiento(LocalDate.of(2001, 1, 3))
                .build();

        Cuenta user5 = CuentaNormal.builder()
                .nombre("User 5")
                .email("user5@mail.com")
                .username("user5")
                .fechaNacimiento(LocalDate.of(2001, 1, 3))
                .build();

        Cuenta pop1 = CuentaPopular.builder()
                .nombre("Pop 1")
                .email("pop1@mail.com")
                .username("pop1")
                .fechaNacimiento(LocalDate.of(2001, 1, 3))
                .build();
        Cuenta pop2 = CuentaPopular.builder()
                .nombre("Pop 2")
                .email("pop2@mail.com")
                .username("pop2")
                .fechaNacimiento(LocalDate.of(2001, 1, 3))
                .build();

        Cuenta pop3 = CuentaPopular.builder()
                .nombre("Pop 3")
                .email("pop3@mail.com")
                .username("pop3")
                .fechaNacimiento(LocalDate.of(2001, 1, 3))
                .build();

        Cuenta empresa1 = CuentaEmpresa.builder()
                .nombre("Empresa 1")
                .email("empresa1@mail.com")
                .username("empresa1")
                .fechaNacimiento(LocalDate.of(2001, 1, 3))
                .direccion("Dirección 1")
                .telefono("+54 261 999 9999")
                .build();

        Cuenta empresa2 = CuentaEmpresa.builder()
                .nombre("Empresa 2")
                .email("empresa2@mail.com")
                .username("empresa2")
                .fechaNacimiento(LocalDate.of(2001, 1, 3))
                .direccion("Dirección 2")
                .telefono("+54 261 999 8888")
                .build();

        return ListUtils.toArrayList(user1, user2, user3, user4, user5, pop1, pop2, pop3, empresa1, empresa2);
    }
}
