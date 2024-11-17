import { Component, OnInit } from '@angular/core';
import {Contacto } from './contacto';
import {ContactosService } from './contactos.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormularioContactoComponent} from './formulario-contacto/formulario-contacto.component'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  contactos: Contacto [] = [];
  contactoElegido?: Contacto;

  constructor(private contactosService: ContactosService, private modalService: NgbModal) { }

  ngOnInit(): void {
    this.contactos = this.contactosService.getContactos();
    this.ordenarContactos();
    
  }

  elegirContacto(contacto: Contacto): void {
    this.contactoElegido = contacto;
  }

  aniadirContacto(): void {
    let ref = this.modalService.open(FormularioContactoComponent);
    ref.componentInstance.accion = "Añadir";
    ref.componentInstance.contacto = {id: 0, nombre: '', apellidos: '', email: '', telefono: ''};
    ref.result.then((contacto: Contacto) => {
      this.contactosService.addContacto(contacto);
      this.contactos = this.contactosService.getContactos();
      this.ordenarContactos();
    }, (reason) => {});

  }
  contactoEditado(contacto: Contacto): void {
    this.contactosService.editarContacto(contacto);
    this.contactos = this.contactosService.getContactos();
    this.contactoElegido = this.contactos.find(c => c.id == contacto.id);
  }

  eliminarContacto(contacto: Contacto): void {
    this.contactosService.eliminarContacto(contacto.id);
    this.contactos = this.contactosService.getContactos();
  }

  editarContacto(contacto: Contacto): void {
    let ref = this.modalService.open(FormularioContactoComponent);
    ref.componentInstance.accion = "Editar";
    ref.componentInstance.contacto = {...contacto};
    ref.result.then((contactoEditado: Contacto) => {
      this.contactosService.editarContacto(contactoEditado);
      this.contactos = this.contactosService.getContactos();
      this.ordenarContactos();
    }, (reason) => {});
  }

  ordenarContactos(){
    this.contactos.sort(this.ordenar);
  }

  ordenar(contacto1: Contacto, contacto2:Contacto):number{
      if(contacto1.nombre < contacto2.nombre) return -1;
      if(contacto1.nombre > contacto2.nombre) return 1;
      return 0;
  }


}
