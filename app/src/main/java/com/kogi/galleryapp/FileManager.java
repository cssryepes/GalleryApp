package com.kogi.galleryapp;

public class FileManager {
//    private static FileManager instance = null;
//
//    public FileManager() {
//    }
//
//    public synchronized static FileManager getInstance() {
//        if (instance == null) {
//            instance = new FileManager();
//        }
//
//        return instance;
//    }
//
//    /**
//     * Crea la carpeta de la App al inicio del aplicativo
//     *
//     * @return path where was create the folder
//     */
//    public String createFolderApp() {
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Constants.EMSA;
//        File dir = new File(path);
//
//        if (!dir.exists())
//            dir.mkdirs();
//
//        return path;
//    }
//
//    /**
//     * Crea carpeta de orden de servicio
//     *
//     * @param codigoOS code to folder name
//     */
//    public void createFolder(String codigoOS) {
//        File folder = new File(EmsaApp.getInstance().path + File.separator + codigoOS);
//        if (!folder.exists())
//            folder.mkdirs();
//    }
//
//    /**
//     * Borra la carpeta de una orden de servicio
//     */
//    public void deleteFolder(String codigoOS) {
//        File folder = new File(EmsaApp.getInstance().path + File.separator + codigoOS);
//        if (folder.exists()) {
//            deleteRecursive(folder);
//        }
//    }
//
//    /**
//     * Borra el contenido que no se necesita de la carpeta
//     */
//    public void deleteUnnecesaryData(String codigoOS) {
//        File folder = new File(EmsaApp.getInstance().path + File.separator + codigoOS);
//        if (folder.exists()) {
//            File[] files = folder.listFiles();
//            for (File file : files) {
//                if (file.getName().contains(Constants.COMPROBANTE_NAME) || file.getName().contains(Constants.COMPROBANTE_MATERIAL)
//                        || file.getName().contains(Constants.IMAGE_NAME)) {
//                    continue;
//                } else {
//                    file.delete();
//                }
//            }
//        }
//    }
//
//    /**
//     * Borra el archivo, si es una carpeta borra recursivamente su contenido
//     */
//    private void deleteRecursive(File fileOrDirectory) {
//        if (fileOrDirectory.isDirectory()) {
//            for (File child : fileOrDirectory.listFiles()) {
//                deleteRecursive(child);
//            }
//        }
//        fileOrDirectory.delete();
//    }
//
//    public boolean renameFolder(String codigoOS, String sufijo) {
//        String path = EmsaApp.getInstance().path + File.separator + codigoOS;
//        File folder = new File(path);
//
//        if (folder.exists()) {
//            return folder.renameTo(new File(path + Constants.IMAGE_SEPARATOR + sufijo));
//        } else {
//            folder = new File(path + Constants.IMAGE_SEPARATOR + sufijo);
//            if (folder.exists()) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    /**
//     * Metodo usado para borrar el sufijo de la Orden, para poder reintentar la legalización.
//     *
//     * @param codigoOS código de la OS para renombrar el folder
//     * @return booleano para validar si se renombro la carpeta
//     */
//    public boolean deleteSufixFolder(String codigoOS, String Sufix) {
//        String path = EmsaApp.getInstance().path + File.separator + codigoOS + Constants.IMAGE_SEPARATOR + Sufix;
//        String newPath = EmsaApp.getInstance().path + File.separator + codigoOS;
//        File folder = new File(path);
//
//        if (folder.exists()) {
//            return folder.renameTo(new File(newPath));
//        } else {
//            folder = new File(newPath);
//            if (folder.exists()) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//
//    public Properties getProperties() {
//        String path = EmsaApp.getInstance().path + File.separator + "config.properties";
//        Properties properties = new Properties();
//        InputStream input = null;
//
//        try {
//            input = new FileInputStream(path);
//            // load a properties file
//            properties.load(input);
//
//            System.out.println(properties.getProperty("URL"));
//        } catch (Exception ex) {
//            EmsaApp.getInstance().print(Log.ERROR, Utilities.getStackTrace(ex));
//            properties = createProperties();
//        } finally {
//            if (input != null) {
//                try {
//                    input.close();
//                } catch (Exception e) {
//                    EmsaApp.getInstance().print(Log.ERROR, Utilities.getStackTrace(e));
//                }
//            }
//        }
//
//        return properties;
//    }
//
//    /**
//     * Listado de rutas de adjuntos para una orden de servicio
//     *
//     * @param codigo acta
//     * @return Images path list
//     */
//    public ArrayList<String> getImagesPathFromOS(String codigo) {
//        ArrayList<String> response = new ArrayList<>();
//        File[] files = new File(EmsaApp.getInstance().path).listFiles();
//
//        for (File file : files) {
//            /*
//             * Se revisan todos los ficheros con extension de imagen dentro de
//			 * la orden de servicio seleccionada
//			 */
//            if (file.isDirectory() && file.getName().compareTo(codigo) == 0) {
//                File[] osFiles = file.listFiles(new FilenameFilter() {
//                    public boolean accept(File dir, String name) {
//                        return name.contains(Constants.IMAGE_NAME);
//                    }
//                });
//
//                for (File osFile : osFiles) {
//                    response.add(osFile.getAbsolutePath());
//                }
//            }
//        }
//
//        return response;
//    }
//
//    /**
//     * Listado de archivos adjuntos adaptados para la subida al servidor
//     */
//    public ArrayList<ImageSIAP> getImagesFromOS(String codigo) {
//        ArrayList<ImageSIAP> result = new ArrayList<>();
//
//        File[] files = new File(EmsaApp.getInstance().path).listFiles();
//
//        for (File file : files) {
//            if (file.isDirectory() && file.getName().compareTo(codigo) == 0) {
//
//                File[] attachFiles = file.listFiles(new FilenameFilter() {
//                    public boolean accept(File dir, String name) {
//                        return name.contains(Constants.IMAGE_NAME);
//                    }
//                });
//
//                for (File attachFile : attachFiles) {
//                    ImageSIAP image = new ImageSIAP();
//                    image.setEncryptedName(Security.MD5(attachFile.getName()));
//                    image.setName(attachFile.getName());
//                    image.setFilePath(attachFile.getAbsolutePath());
//                    image.setCodigoOS(codigo);
//
//                    result.add(image);
//                }
//
//            }
//        }
//
//        return result;
//    }
//
//    /**
//     * Retorna objeto ImageSIAP para el comprobante con el que se guarda en el
//     * servidor a partir de un codigo de OS
//     */
//    public ImageSIAP getComprobanteFromOS(String codigo) {
//        File[] files = new File(EmsaApp.getInstance().path).listFiles();
//
//        for (File file : files) {
//            if (file.isDirectory() && file.getName().compareTo(codigo) == 0) {
//
//                File[] attachFiles = file.listFiles(new FilenameFilter() {
//                    public boolean accept(File dir, String name) {
//                        return name.contains(Constants.COMPROBANTE_NAME);
//                    }
//                });
//
//                if (attachFiles.length > 0) {
//                    ImageSIAP image = new ImageSIAP();
//                    image.setEncryptedName(Security.MD5(attachFiles[0].getName()));
//                    image.setName(attachFiles[0].getName());
//                    image.setFilePath(attachFiles[0].getAbsolutePath());
//                    image.setCodigoOS(codigo);
//
//                    return image;
//                }
//            }
//        }
//
//        return null;
//    }
//
//    /**
//     * Retorna objeto ImageSIAP para el comprobante con el que se guarda en el
//     * servidor a partir de un codigo de OS
//     */
//    public ImageSIAP getComprobanteMaterialesFromOS(String codigo) {
//        File[] files = new File(EmsaApp.getInstance().path).listFiles();
//
//        for (File file : files) {
//            if (file.isDirectory() && file.getName().compareTo(codigo) == 0) {
//
//                File[] attachFiles = file.listFiles(new FilenameFilter() {
//                    public boolean accept(File dir, String name) {
//                        return name.contains(Constants.COMPROBANTE_MATERIAL);
//                    }
//                });
//
//                if (attachFiles.length > 0) {
//                    ImageSIAP image = new ImageSIAP();
//                    image.setEncryptedName(Security.MD5(attachFiles[0].getName()));
//                    image.setName(attachFiles[0].getName());
//                    image.setFilePath(attachFiles[0].getAbsolutePath());
//                    image.setCodigoOS(codigo);
//
//                    return image;
//                }
//            }
//        }
//
//        return null;
//    }
//
//    /**
//     * Genera el nombre de el nuevo adjunto
//     *
//     * @param codigo order code
//     * @return image uri
//     */
//    public Uri getUriForNewFile(String codigo) {
//        /*
//         * Lista carpetas dentro del directorio EMSA
//		 */
//        StringBuilder response = new StringBuilder();
//        File[] files = new File(EmsaApp.getInstance().path).listFiles();
//        for (File file : files) {
//            /*
//             * Se revisan todos los ficheros con extension de imagen dentro de
//			 * la orden de servicio seleccionada
//			 */
//            if (file.isDirectory() && file.getName().compareTo(codigo) == 0) {
//
//                File[] osFiles = file.listFiles(new FilenameFilter() {
//                    public boolean accept(File dir, String name) {
//                        return name.contains(Constants.IMAGE_NAME);
//                    }
//                });
//
//                int last = 0;
//
//                for (File fileTemp : osFiles) {
//                    String[] data = fileTemp.getName().split(Constants.IMAGE_NAME);
//                    String num = data[data.length - 1].replace(Constants.IMAGE_EXTENSION, "");
//                    if (Integer.parseInt(num) > last)
//                        last = Integer.parseInt(num);
//                }
//
//                response.append(file.getAbsolutePath()).append(File.separator).append(codigo)
//                        .append(Constants.IMAGE_SEPARATOR).append(Constants.IMAGE_NAME).append(last + 1)
//                        .append(Constants.IMAGE_EXTENSION);
//            }
//        }
//
//        File file = new File(response.toString());
//
//        return Uri.fromFile(file);
//    }
//
//    /**
//     * Retorna una URI a partir de la orden de servicio y el nombre del archivo,
//     * en caso tal de que no tenga una orden de servicio se asume que es la
//     * firma del funcionario
//     *
//     * @param codigo code
//     * @param name   key
//     * @return image path
//     */
//    public Uri getUriFromPath(String codigo, String name) {
//        createFolderApp();
//        StringBuilder response = new StringBuilder();
//
//        if (codigo != null) {
//            File[] files = new File(EmsaApp.getInstance().path).listFiles();
//            for (File file : files) {
//                /*
//                 * Se revisan todos los ficheros con extension de imagen dentro
//				 * de la orden de servicio seleccionada
//				 */
//                if (file.isDirectory() && file.getName().compareTo(codigo) == 0) {
//                    response.append(file.getAbsolutePath()).append(File.separator).append(codigo)
//                            .append(Constants.IMAGE_SEPARATOR).append(name).append(Constants.IMAGE_EXTENSION);
//                }
//            }
//        } else {
//            response.append(EmsaApp.getInstance().path).append(File.separator)
//                    .append(Constants.Developer.USER).append(Constants.IMAGE_SEPARATOR).append(name)
//                    .append(Constants.IMAGE_EXTENSION);
//        }
//
//        File file = new File(response.toString());
//
//        return Uri.fromFile(file);
//    }
//
//    /**
//     * Obtiene el numero de archivos adjuntos de una orden de servicio
//     *
//     * @param codigo order code
//     * @return number of files
//     */
//    public int getNumberOfFiles(String codigo) {
//        File[] response = null;
//        File[] files = new File(EmsaApp.getInstance().path).listFiles();
//
//        for (File file : files) {
//            if (file.isDirectory() && file.getName().compareTo(codigo) == 0) {
//                response = file.listFiles(new FilenameFilter() {
//                    public boolean accept(File dir, String name) {
//                        return name.contains(Constants.IMAGE_NAME);
//                    }
//                });
//            }
//        }
//
//        if (response != null)
//            return response.length;
//        else
//            return 0;
//    }
//
//    public boolean folderExistAsError(String acta) {
//        File[] files = new File(EmsaApp.getInstance().path).listFiles();
//
//        for (File file : files) {
//            if (file.isDirectory() && file.getName().contains(acta) && file.getName().contains(Constants.ERROR)) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    /**
//     * Borra archivo a partir de una ruta
//     *
//     * @param imagePath image path will be delete
//     * @return boolean
//     */
//    public boolean deleteFile(String imagePath) {
//        File imagen = new File(imagePath);
//
//        return imagen.getAbsoluteFile().delete();
//    }
//
//    /**
//     * Borra archivo a partir de una Uri
//     */
//    public boolean deleteFile(Uri uri) {
//        File imagen = new File(uri.getPath());
//
//        return imagen.getAbsoluteFile().delete();
//    }
//
//    private Properties createProperties() {
//        String path = EmsaApp.getInstance().path + File.separator + "config.properties";
//        Properties properties = new Properties();
//        OutputStream output = null;
//
//        try {
//            output = new FileOutputStream(path);
//
//            // set the properties value
//            properties.setProperty("URL", "http://190.0.36.70:53131/api/");
//            properties.setProperty("code", "Z,A,M,B,V,R,G,F,N,T,O,AM");
//            properties.setProperty("description", "Azul,Amarillo,Morado,Blanco,Verde,Rojo,Gris,Fucsia,Negro,Transparente,Naranjado,Agua Marina");
//            // save properties to project root folder
//            properties.store(output, null);
//        } catch (Exception e) {
//            EmsaApp.getInstance().print(Log.ERROR, Utilities.getStackTrace(e));
//        } finally {
//            if (output != null) {
//                try {
//                    output.close();
//                } catch (Exception e) {
//                    EmsaApp.getInstance().print(Log.ERROR, Utilities.getStackTrace(e));
//                }
//            }
//        }
//
//        return properties;
//    }
}