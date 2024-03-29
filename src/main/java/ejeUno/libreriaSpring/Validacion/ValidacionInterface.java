package ejeUno.libreriaSpring.Validacion;

import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;

public interface ValidacionInterface {

    default void validacionNombrePersona(String nombre) throws MiExcepcion {
        try {
            if (nombre == null) {
                throw new MiExcepcion("Valor sin declarar");
            } else if (nombre.trim().isEmpty()) {
                throw new MiExcepcion("Nombre invalido, no puede estar en blanco");
            } else if (nombre.length() < 1) {
                throw new MiExcepcion("Nombre invalido, debe tener mas de una letra");
            }
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    default void validacionPresencia(Optional respuesta, String nombreObjeto) throws MiExcepcion {
        try {
            if (!respuesta.isPresent()) {
                throw new MiExcepcion(nombreObjeto + " seleccionado no existe");
            }
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    default void validacionPresencia(Object entity, String nombreObjeto) throws MiExcepcion {
        try {
            if (entity == null) {
                throw new MiExcepcion("Error al identificar " + nombreObjeto);
            }
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    default void validacionNombre(String nombre, String tipo) throws MiExcepcion {
        try {
            if (nombre == null) {
                throw new MiExcepcion("Valor vacio");
            } else if (nombre.trim().isEmpty()) {
                throw new MiExcepcion(tipo + " invalido, no puede estar en blanco");
            } else if (nombre.length() < 0) {
                throw new MiExcepcion(tipo + " invalido, debe tener mas de un caracter");
            }
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    default void validacionAnio(Integer anio) throws MiExcepcion {
        try {
            Calendar anioActual = Calendar.getInstance();
            if (anio == null) {
                throw new MiExcepcion("Valor sin declarar");
            } else if (anio.toString().length() < 4) {
                throw new MiExcepcion("Año invalido");
            } else if (anio > anioActual.get(Calendar.YEAR)) {
                throw new MiExcepcion("Marty McFly eres tu?");
            }
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    default void validacionCantidadEjemplar(Integer ejemplares) throws MiExcepcion {
        try {
            if (ejemplares == null) {
                throw new MiExcepcion("Valor sin declarar");
            } else if (ejemplares < 0) {
                throw new MiExcepcion("La cantidad no puede ser menor que cero");
            }
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    default void validacionIsbn(Long isbn, Long cant) throws MiExcepcion {
        try {
            if (isbn == null) {
                throw new MiExcepcion("Valor sin declarar");
            } else if (!isbn.toString().matches("^(978)[0-9]{10}$")) {
                throw new MiExcepcion("ISBN invalido. El isbn debe comenzar con 978 y contener 13 digitos");
            } else if (cant > 0) {
                throw new MiExcepcion("ISBN invalido. existe un libro activo con el mismo ISBN: " + isbn);
            }
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    default void validacionEstado(Boolean estado, String tipo) throws MiExcepcion {
        try {
            if (estado == null) {
                throw new MiExcepcion("Error, no se espcifica si esta habilitado o deshabilitado");
            } else if (estado == false) {
                throw new MiExcepcion("'" + tipo + "' no se encuentra habilitado");
            }
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    default void validacionTransaccion(Integer cantTransaccion, Integer cantActual, Integer cantTotal) throws MiExcepcion {
        try {
            if (cantTransaccion == null) {
                throw new MiExcepcion("Cantidad de transaccion sin declarar");
            } else if (cantActual == null) {
                throw new MiExcepcion("Cantidad actual sin declarar");
            } else if (cantTotal == null) {
                throw new MiExcepcion("Cantidad total sin declarar");
            } else if (cantTransaccion == 0) {
                throw new MiExcepcion("Valor de transaccion no puede ser cero");
            } else if (cantTransaccion < 0) {
                throw new MiExcepcion("Valor de transaccion no puede ser negativo");
            } else if (cantTransaccion > cantTotal) {
                throw new MiExcepcion("Cantidad supera el total de ejemplares");
            } else if (cantTransaccion > cantActual) {
                throw new MiExcepcion("Cantidad supera el maximo para esta transaccion");
            }
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    default void validacionDocumento(Long documento) throws MiExcepcion {
        try {
            if (documento == null) {
                throw new MiExcepcion("Documento no fue cargado");
            } else if (documento < 0) {
                throw new MiExcepcion("Documento invalido, no puede ser un numero negativo");
            } else if (Long.toString(documento).matches("^[0-9][^a-zA-Z]{6,9}$") == false) {
                throw new MiExcepcion("Documento invalido");
            }
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    default void validacionTelefono(String telefono) throws MiExcepcion {
        try {
            if (telefono == null) {
                throw new MiExcepcion("Telefono no fue cargado");
            } else if (telefono.length() < 7) {
                throw new MiExcepcion("Telefono invalido, no puede tener menos de 7 digitos");
            }
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    default void validacionFechaDevolucion(LocalDate fechaDevolucion) throws MiExcepcion {
        try {
            LocalDate actual = LocalDate.now();
            if (fechaDevolucion == null) {
                throw new MiExcepcion("La fecha de devolucion no fue cargada");
            } else if (fechaDevolucion.isEqual(actual)) {
                throw new MiExcepcion("La fecha de devolucion no puede ser la misma que la actual");
            } else if (fechaDevolucion.isBefore(actual)) {
                throw new MiExcepcion("Marty McFly eres tu?");
            } else if (fechaDevolucion.isAfter(actual.plusYears(1))) {
                throw new MiExcepcion("El tiempo maximo de prestamo es de un año");
            }

        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    default void validacionPrestados(Integer cantidad) throws MiExcepcion {
        try {
            if (cantidad == null) {
                throw new MiExcepcion("Error al identificar cantidad de libros prestados");
            } else if (cantidad > 0) {
                throw new MiExcepcion("No se puede modificar, Aún Quedan ejemplares sin regresar");
            }

        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    default void validacionPrestados(Long cantidad) throws MiExcepcion {
        try {
            if (cantidad == null) {
                throw new MiExcepcion("Error al identificar cantidad de libros prestados");
            } else if (cantidad > 0) {
                throw new MiExcepcion("Quedan ejemplares sin regresar");
            }

        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    default void validacionClave(String clave) throws MiExcepcion {
        try {
            if (clave == null) {
                throw new MiExcepcion("Campo de clave no puede estar vacio");
            } else if (clave.trim().isEmpty()) {
                throw new MiExcepcion("Campo de clave no puede estar en blanco");
            } else if (clave.length() < 8) {
                throw new MiExcepcion("Clave deebe tener al menos 8 digitos");
            } else if (clave.equals("Clave123")) {
                throw new MiExcepcion("En serio? definitivamente la clave no puede ser Clave123");
            }
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }
}
