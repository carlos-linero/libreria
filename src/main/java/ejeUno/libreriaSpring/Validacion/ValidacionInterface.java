package ejeUno.libreriaSpring.Validacion;

import ejeUno.libreriaSpring.Excepciones.MiExcepcion;
import java.util.Calendar;
import java.util.Date;
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

    default void validaPresencia(Optional respuesta, String nombreObjeto) throws MiExcepcion {
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

    default void validaPresencia(Object entity, String nombreObjeto) throws MiExcepcion {
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

    default void validacionNombre(String nombre) throws MiExcepcion {
        try {
            if (nombre == null) {
                throw new MiExcepcion("Valor vacio");
            } else if (nombre.trim().isEmpty()) {
                throw new MiExcepcion("Nombre invalido, no puede estar en blanco");
            } else if (nombre.length() < 0) {
                throw new MiExcepcion("Nombre invalido, debe tener mas de una letra");
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

    default void validaCantidadEjemplar(Integer ejemplares) throws MiExcepcion {
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

    default void validaIsbn(Long isbn, Long cant) throws MiExcepcion {
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

    default void validaEstado(Boolean estado) throws MiExcepcion {
        try {
            if (estado == null) {
                throw new MiExcepcion("Error, no se espcifica si esta habilitado o deshabilitado");
            }else if (estado == false) {
                throw new MiExcepcion("No se encuentra habilitado");
            }
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    default void validaTransaccion(Integer cantTransaccion, Integer cantActual, Integer cantTotal) throws MiExcepcion {
        try {
            if (cantTransaccion == null) {
                throw new MiExcepcion("Valor de cantidad de prestamo sin declarar");
            } else if (cantActual == null) {
                throw new MiExcepcion("Valor de cantidad actual sin declarar");
            } else if (cantTransaccion > cantTotal) {
                throw new MiExcepcion("Cantidad del prestamo supera a la cantidad total");
            } else if (cantTransaccion > cantActual) {
                throw new MiExcepcion("Cantidad del prestamo supera a la cantidad actual");
            }
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    default void validaDocumento(Long documento) throws MiExcepcion {
        try {
            if (documento == null) {
                throw new MiExcepcion("Documento no fue cargado");
            } else if (documento < 0) {
                throw new MiExcepcion("Documento invalido, no puede ser un numero negativo");
            } else if (Long.toString(documento).matches("^[0-9][^a-zA-Z]{6,9}$")==false) {
                throw new MiExcepcion("Documento invalido");
            }
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }

    default void validaTelefono(String telefono) throws MiExcepcion {
        try {
            if (telefono == null) {
                throw new MiExcepcion("Telefono no fue cargado");
            } else if (telefono.length()<7) {
                throw new MiExcepcion("Telefono invalido, no puede tener menos de 7 digitos");
            } 
        } catch (MiExcepcion es) {
            throw es;
        } catch (Exception e) {
            throw e;
        }
    }
}
